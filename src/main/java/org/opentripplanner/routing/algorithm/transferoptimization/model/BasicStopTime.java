package org.opentripplanner.routing.algorithm.transferoptimization.model;

import org.opentripplanner.model.base.ValueObjectToStringBuilder;

import java.util.Objects;

/** Basic stop and time value object. */
final class BasicStopTime implements StopTime {
  private final int stop;
  private final int time;

  BasicStopTime(int stop, int time) {
    this.stop = stop;
    this.time = time;
  }

  @Override
  public int stop() { return stop; }

  @Override
  public int time() { return time; }

  @Override
  public String toString() {
    return ValueObjectToStringBuilder.of()
        .addLbl("[")
        .addNum(stop)
        .addServiceTime(time)
        .addLbl("]")
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    BasicStopTime that = (BasicStopTime) o;
    return stop == that.stop && time == that.time;
  }

  @Override
  public int hashCode() {
    return Objects.hash(stop, time);
  }
}