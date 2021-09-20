package org.opentripplanner.transit.raptor._data.transit;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opentripplanner.model.base.ToStringBuilder;
import org.opentripplanner.transit.raptor.api.transit.RaptorConstrainedTransferSearch;
import org.opentripplanner.transit.raptor.api.transit.RaptorRoute;
import org.opentripplanner.transit.raptor.api.transit.RaptorTimeTable;
import org.opentripplanner.transit.raptor.api.transit.RaptorTripPattern;

public class TestRoute implements RaptorRoute<TestTripSchedule>, RaptorTimeTable<TestTripSchedule> {

    private final TestTripPattern pattern;
    private final List<TestTripSchedule> schedules = new ArrayList<>();
    private final TestTransferSearch transferConstraintsForwardSearch = new TestTransferSearch();
    private final TestTransferSearch transferConstraintsReverseSearch = new TestTransferSearch();


    private TestRoute(TestTripPattern pattern) {
        this.pattern = pattern;
    }

    public static TestRoute route(TestTripPattern pattern){
        return new TestRoute(pattern);
    }

    public static TestRoute route(String name, int ... stopIndexes){
        return route(TestTripPattern.pattern(name, stopIndexes));
    }

    @Override
    public TestTripSchedule getTripSchedule(int index) {
        return schedules.get(index);
    }

    @Override
    public int numberOfTripSchedules() {
        return schedules.size();
    }

    @Override
    public RaptorTimeTable<TestTripSchedule> timetable() {
        return this;
    }

    @Override
    public RaptorTripPattern pattern() {
        return pattern;
    }

    @Override
    public RaptorConstrainedTransferSearch<TestTripSchedule> constrainedTransfersReverseSearch() {
        return transferConstraintsReverseSearch;
    }

    @Override
    public RaptorConstrainedTransferSearch<TestTripSchedule> constrainedTransfersForwardSearch() {
        return transferConstraintsForwardSearch;
    }

    public List<TestGuaranteedTransferBoarding> listTransferConstraintsForwardSearch() {
        return transferConstraintsForwardSearch.transferConstraints();
    }

    public TestRoute withTimetable(TestTripSchedule ... trips) {
        Collections.addAll(schedules, trips);
        return this;
    }

    public TestRoute withTimetable(TestTripSchedule.Builder ... scheduleBuilders) {
        for (TestTripSchedule.Builder builder : scheduleBuilders) {
            var tripSchedule = builder.pattern(pattern).build();
            schedules.add(tripSchedule);
        }
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.of(TestRoute.class)
                .addObj("pattern", pattern)
                .addObj("schedules", schedules)
                .toString();
    }

    void addGuaranteedTxForwardSearch(
            TestTripSchedule sourceTrip,
            int sourceStopPos,
            TestTripSchedule targetTrip,
            int targetTripIndex,
            int targetStopPos
    ) {
        int targetTime = targetTrip.arrival(targetStopPos);

        this.transferConstraintsForwardSearch.addGuaranteedTransfers(
                sourceTrip, sourceStopPos, targetTrip, targetTripIndex, targetStopPos, targetTime
        );
    }

    /**
     * Reverse search transfer, the {@code source/target} is the trips in order of the reverse
     * search, which is opposite from {@code from/to} in the result path.
     */
    void addGuaranteedTxReverseSearch(
            TestTripSchedule sourceTrip,
            int sourceStopPos,
            TestTripSchedule targetTrip,
            int targetTripIndex,
            int targetStopPos
            ) {
        final int targetTime = targetTrip.departure(targetStopPos);
        // This is used in the revers search
        this.transferConstraintsReverseSearch.addGuaranteedTransfers(
                sourceTrip, sourceStopPos, targetTrip, targetTripIndex, targetStopPos, targetTime
        );
    }
}
