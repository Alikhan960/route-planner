package kz.aitu.navigation.strategy;

import java.util.List;

import kz.aitu.navigation.model.Location;
import kz.aitu.navigation.model.Route;

/**
 * Walking: can cut through pedestrian paths, but is slow.
 */
public class WalkStrategy implements RouteStrategy {

    private static final String TRAVEL_MODE = "Walk";
    private static final double PEDESTRIAN_PATH_FACTOR = 1.1;
    private static final double AVERAGE_SPEED_KMH = 5.0;

    @Override
    public Route buildRoute(Location start, Location destination) {
        double distanceKm = start.straightLineDistanceTo(destination) * PEDESTRIAN_PATH_FACTOR;
        long minutes = Math.round(distanceKm / AVERAGE_SPEED_KMH * 60);
        List<String> steps = List.of(
                "Leave " + start.name() + " on foot",
                "Follow sidewalks and pedestrian paths towards " + destination.name(),
                "Arrive at " + destination.name());
        return new Route(TRAVEL_MODE, distanceKm, minutes, steps);
    }
}
