package org.opentripplanner.standalone.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import org.opentripplanner.routing.algorithm.raptor.router.AdditionalSearchDays;

class AdditionalSearchDaysTest {

    @Test
    void middleOfDay() {
        var req = getDays("2022-01-25T13:14:20+01:00", false);
        assertEquals(0, req.additionalSearchDaysInFuture());
        assertEquals(0, req.additionalSearchDaysInPast());

        var req2 = getDays("2022-01-25T13:14:20+01:00", true);
        assertEquals(0, req2.additionalSearchDaysInFuture());
        assertEquals(0, req2.additionalSearchDaysInPast());
    }

    @Test
    void closeToMidnight() {
        var req = getDays("2022-01-25T23:14:20+01:00", false);

        assertEquals(0, req.additionalSearchDaysInPast());
        assertEquals(1, req.additionalSearchDaysInFuture());

        var req2 = getDays("2022-01-25T23:14:20+01:00", true);
        assertEquals(0, req2.additionalSearchDaysInPast());
        assertEquals(0, req2.additionalSearchDaysInFuture());
    }

    @Test
    void shortlyAfterMidnight() {
        var req = getDays("2022-01-25T00:15:25+01:00", false);

        assertEquals(0, req.additionalSearchDaysInPast());
        assertEquals(0, req.additionalSearchDaysInFuture());

        var req2 = getDays("2022-01-25T00:15:25+01:00", true);
        assertEquals(1, req2.additionalSearchDaysInPast());
        assertEquals(0, req2.additionalSearchDaysInFuture());
    }

    private AdditionalSearchDays getDays(String time, boolean arriveBy) {
        var zonedDateTime =
                OffsetDateTime.parse(time).atZoneSameInstant(ZoneId.of("Europe/Berlin"));
        return new AdditionalSearchDays(
                arriveBy,
                zonedDateTime,
                Duration.ofHours(2),
                Duration.ofHours(6),
                Duration.ofHours(6)
        );
    }

}