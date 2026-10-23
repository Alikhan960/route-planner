package kz.aitu.navigation.strategy;

import java.util.List;

import kz.aitu.navigation.model.Location;
import kz.aitu.navigation.model.Route;

/**
 * Public transport: fixed lines and a waiting time at the stop.
 * Added after the first three strategies, without modifying any existing class.
 */
public class PublicTransportStrategy implements RouteStrategy {

    private static final String TRAVEL_MODE = "Public transport";
    private static final double LINE_DETOUR_FACTOR = 1.4;
    private static final double AVERAGE_SPEED_KMH = 25.0;
    private static final long AVERAGE_WAIT_MINUTES = 7;

    @Override
    public Route buildRoute(Location start, Location destination) {
        double distanceKm = start.straightLineDistanceTo(destination) * LINE_DETOUR_FACTOR;
        long ridingMinutes = Math.round(distanceKm / AVERAGE_SPEED_KMH * 60);
        List<String> steps = List.of(
                "Walk from " + start.name() + " to the nearest stop",
                "Wait for the bus (about " + AVERAGE_WAIT_MINUTES + " min)",
                "Ride towards " + destination.name());
        return new Route(TRAVEL_MODE, distanceKm, AVERAGE_WAIT_MINUTES + ridingMinutes, steps);
    }
}
