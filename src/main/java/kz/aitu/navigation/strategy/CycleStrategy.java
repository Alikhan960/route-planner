package kz.aitu.navigation.strategy;

import java.util.List;

import kz.aitu.navigation.model.Location;
import kz.aitu.navigation.model.Route;

/**
 * Cycling: prefers bike lanes, so it is a little longer than a straight line.
 */
public class CycleStrategy implements RouteStrategy {

    private static final String TRAVEL_MODE = "Bicycle";
    private static final double BIKE_LANE_FACTOR = 1.15;
    private static final double AVERAGE_SPEED_KMH = 15.0;

    @Override
    public Route buildRoute(Location start, Location destination) {
        double distanceKm = start.straightLineDistanceTo(destination) * BIKE_LANE_FACTOR;
        long minutes = Math.round(distanceKm / AVERAGE_SPEED_KMH * 60);
        List<String> steps = List.of(
                "Unlock the bike at " + start.name(),
                "Ride along bike lanes towards " + destination.name(),
                "Lock the bike at " + destination.name());
        return new Route(TRAVEL_MODE, distanceKm, minutes, steps);
    }
}
