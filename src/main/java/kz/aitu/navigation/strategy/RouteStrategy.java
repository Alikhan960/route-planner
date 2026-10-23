package kz.aitu.navigation.strategy;

import kz.aitu.navigation.model.Location;
import kz.aitu.navigation.model.Route;

/**
 * Strategy interface: one way of travelling from a start point to a destination.
 */
public interface RouteStrategy {

    Route buildRoute(Location start, Location destination);
}
