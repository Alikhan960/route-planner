package kz.aitu.navigation;

import java.util.Objects;

import kz.aitu.navigation.model.Location;
import kz.aitu.navigation.model.Route;
import kz.aitu.navigation.strategy.RouteStrategy;

/**
 * Context: plans routes by delegating to whichever strategy it currently holds.
 * It knows nothing about cars, bikes or feet.
 */
public class Navigator {

    private RouteStrategy strategy;

    public Navigator(RouteStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(RouteStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "strategy must not be null");
    }

    public Route planRoute(Location start, Location destination) {
        return strategy.buildRoute(start, destination);
    }
}
