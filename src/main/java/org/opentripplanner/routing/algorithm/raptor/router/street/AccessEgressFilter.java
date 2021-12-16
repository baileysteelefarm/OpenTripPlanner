package org.opentripplanner.routing.algorithm.raptor.router.street;

import org.opentripplanner.model.Station;
import org.opentripplanner.model.Stop;
import org.opentripplanner.model.TripPattern;
import org.opentripplanner.routing.api.request.RoutingRequest;
import org.opentripplanner.routing.api.request.StreetMode;
import org.opentripplanner.routing.edgetype.VehicleParkingEdge;
import org.opentripplanner.routing.graph.Edge;
import org.opentripplanner.routing.graph.GraphIndex;
import org.opentripplanner.routing.graphfinder.NearbyStop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Used to filter out unwanted access/egress legs. For walking and biking providing a lot of initial
 * states is not a problem, because most of them will quickly be dominated by other states. When
 * using modes that involve driving, however, we will get a lot more optimal states. This is because
 * driving is often much faster than transit, while also being configured to have a higher
 * generalized cost (to discourage driving over transit). This will lead to almost every
 * access/egress state to have a pareto optimal combination of time and cost, leading to much worse
 * performance for the Raptor search.
 */
public class AccessEgressFilter {

  static Collection<NearbyStop> filter(
      Collection<NearbyStop> nearbyStops, StreetMode streetMode, RoutingRequest request
  ) {
    switch (streetMode) {
      case CAR_PICKUP:
      case CAR_TO_PARK:
        return filterByClosestStationsPerPatterns(
            nearbyStops,
            request.maxNumberOfStationsForCarAccessEgress,
            request.rctx.graph.index
        );
      default:
        return nearbyStops;
    }
  }

  /**
   * Returns the closest stations for each TripPattern.
   */
  private static Collection<NearbyStop> filterByClosestStationsPerPatterns(
      Collection<NearbyStop> nearbyStops, int maxNumberOfStations, GraphIndex graphIndex
  ) {
    if (nearbyStops.isEmpty()) { return nearbyStops; }

    // Because order is kept using these stream operators, we will have unique stations by distance
    List<Station> stationsByDistance = nearbyStops
        .stream()
        .sorted()
        .filter(s -> s.stop instanceof Stop)
        .map(s -> ((Stop) s.stop).getParentStation())
        .distinct()
        .collect(Collectors.toList());

    Set<TripPattern> tripPatternsEncountered = new HashSet<>();
    List<Station> resultByStation = new ArrayList<>();

    for (Station station : stationsByDistance) {
      Set<TripPattern> patternsForStation = station
          .getChildStops()
          .stream()
          .flatMap(s -> graphIndex.getPatternsForStop(s).stream())
          .collect(Collectors.toSet());

      // Returns true if at least one new pattern was added to the set
      if (tripPatternsEncountered.addAll(patternsForStation)) {
        resultByStation.add(station);
        // Break out of the loop when we have found the required number of stations
        if (resultByStation.size() == maxNumberOfStations) { break; }
      }
    }

    // Map back to NearbyStop objects
    List<NearbyStop> result = nearbyStops
        .stream()
        .filter(s -> s.stop instanceof Stop)
        .filter(s -> resultByStation.contains(((Stop) s.stop).getParentStation()))
        .collect(Collectors.toList());

    return result;
  }

  /**
   * Returns the closest stops by distance. In addition, all other stops within the same stations
   * as the closest stops are added.
   */
  private static Collection<NearbyStop> filterCarPickup(
      Collection<NearbyStop> nearbyStops, int maxCarPickupAccessEgressStops
  ) {
    // Add all stops locations (including areas) within range to result
    Set<NearbyStop> result = nearbyStops.stream()
        // Sorts by least distance
        .sorted()
        .limit(maxCarPickupAccessEgressStops)
        .collect(Collectors.toSet());

    // Get the station for the stops within range
    Set<Station> stations = result
        .stream()
        .filter(s -> s.stop instanceof Stop)
        .map(s -> ((Stop) s.stop).getParentStation())
        .collect(Collectors.toSet());

    // All the child stops that are outside of the range
    result.addAll(
        nearbyStops
        .stream()
        .filter(s -> s.stop instanceof Stop)
        .filter(s -> stations.contains(((Stop) s.stop).getParentStation()))
        .collect(Collectors.toList())
    );

    return result;
  }

  /**
   * Returns the closest stops by distance. In addition, all other stops using the same car park
   * are added.
   */
  private static Collection<NearbyStop> filterByCarPark(
      Collection<NearbyStop> nearbyStops, int maxCarParkAccessEgressStops
  ) {
    // Add all stops locations within range to result
    Set<NearbyStop> result = nearbyStops.stream()
        // Sorts by least distance
        .sorted()
        .limit(maxCarParkAccessEgressStops)
        .collect(Collectors.toSet());

    // Get the car park used for the stops within range
    Set<Edge> parAndRideEdges = result
        .stream()
        .filter(s -> s.edges.stream().anyMatch(e -> e instanceof VehicleParkingEdge))
        .map(s -> s.edges.stream().filter(e -> e instanceof VehicleParkingEdge).findFirst().get())
        .collect(Collectors.toSet());

    // Add all the stops that use the same car park as stops within range
    result.addAll(
        nearbyStops
            .stream()
            .filter(s -> s.edges.stream().anyMatch(parAndRideEdges::contains))
            .collect(Collectors.toList())
    );

    return result;
  }

  /**
   * Returns the stops within a radius of either the minDistance parameters or the distance of
   * the closest stop multiplied by the distanceFactor parameter, whichever is highest.
   */
  private static Collection<NearbyStop> filterByDistanceOfNearestStop(
      Collection<NearbyStop> nearbyStops, double minDistance, double distanceFactor
  ) {
    if (!nearbyStops.isEmpty()) { return nearbyStops; }

    double closestStopDistance = nearbyStops.stream().sorted().findFirst().get().distance;
    double distanceLimit = Double.max(closestStopDistance * distanceFactor, minDistance);

    return nearbyStops
        .stream()
        .filter(s -> s.distance <= distanceLimit)
        .collect(Collectors.toList());
  }
}