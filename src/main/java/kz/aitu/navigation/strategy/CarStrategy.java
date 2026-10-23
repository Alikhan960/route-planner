package kz.aitu.navigation.strategy;

import java.util.List;

import kz.aitu.navigation.model.Location;
import kz.aitu.navigation.model.Route;

/**
 * Driving: follows the road network, so it is longer than a straight line, but fast.
 */
public class CarStrategy implements RouteStrategy {

    private static final String TRAVEL_MODE = "Car";
    private static final double ROAD_DETOUR_FACTOR = 1.3;
    private static final double AVERAGE_SPEED_KMH = 50.0;

    @Override
    public Route buildRoute(Location start, Location destination) {
        double distanceKm = start.straightLineDistanceTo(destination) * ROAD_DETOUR_FACTOR;
        long minutes = Math.round(distanceKm / AVERAGE_SPEED_KMH * 60);
        List<String> steps = List.of(
                "Start the car at " + start.name(),
                "Take the main roads towards " + destination.name(),
                "Park near " + destination.name());
        return new Route(TRAVEL_MODE, distanceKm, minutes, steps);
    }
}
