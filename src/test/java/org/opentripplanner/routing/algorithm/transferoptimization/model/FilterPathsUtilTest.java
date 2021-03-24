package org.opentripplanner.routing.algorithm.transferoptimization.model;

import static org.junit.Assert.assertEquals;
import static org.opentripplanner.transit.raptor._data.stoparrival.BasicPathTestCase.COST_CALCULATOR;
import static org.opentripplanner.util.time.TimeUtils.time;

import java.util.List;
import org.junit.Test;
import org.opentripplanner.transit.raptor._data.RaptorTestConstants;
import org.opentripplanner.transit.raptor._data.api.PathBuilder;
import org.opentripplanner.transit.raptor._data.stoparrival.BasicPathTestCase;
import org.opentripplanner.transit.raptor._data.transit.TestTripSchedule;
import org.opentripplanner.transit.raptor.api.path.Path;

public class FilterPathsUtilTest implements RaptorTestConstants {

  private static final int TRANSIT_TIME = 2000 - (BOARD_SLACK + ALIGHT_SLACK);

  private final PathBuilder pathBuilder = new PathBuilder(ALIGHT_SLACK, COST_CALCULATOR);

  Path<TestTripSchedule> pathA = pathBuilder
      .access(time("10:00:15"), D2m, STOP_A)
      .bus("L11", time("10:03"), TRANSIT_TIME, STOP_B)
      .egress(D2m);

  Path<TestTripSchedule> pathB = BasicPathTestCase.basicTripAsPath();

  @Test
  public void filterEmptyList() {
    assertEquals(List.of(), FilterPathsUtil.filter(List.of(), Path::generalizedCost));
  }

  @Test
  public void filterOnePath() {
    assertEquals(List.of(pathA), FilterPathsUtil.filter(List.of(pathA), Path::generalizedCost));
  }

  @Test
  public void filterDifferentPaths() {
    assertEquals(List.of(pathA), FilterPathsUtil.filter(List.of(pathA, pathB), Path::generalizedCost));
  }

  @Test
  public void filterTwoPathsWithTheSameCost() {
    assertEquals(List.of(pathA, pathA), FilterPathsUtil.filter(List.of(pathA, pathA), Path::generalizedCost));
  }
}