package kz.aitu.navigation;

import kz.aitu.navigation.model.Location;
import kz.aitu.navigation.model.Route;
import kz.aitu.navigation.strategy.CarStrategy;
import kz.aitu.navigation.strategy.CycleStrategy;
import kz.aitu.navigation.strategy.PublicTransportStrategy;
import kz.aitu.navigation.strategy.RouteStrategy;
import kz.aitu.navigation.strategy.WalkStrategy;

/**
 * Dependency-free tests (plain Java, no framework). Run via the instructions in README.md.
 * Each test checks one concept and is independent of the others.
 */
public class NavigatorTest {

    private static final Location START = new Location("A", 0, 0);
    private static final Location DESTINATION = new Location("B", 3, 4); // 5 km straight line

    private static int failures = 0;

    public static void main(String[] args) {
        run("car route uses road detour and car speed", NavigatorTest::carRouteUsesRoadDetourAndSpeed);
        run("walk route uses pedestrian factor and walking speed", NavigatorTest::walkRouteUsesPedestrianFactorAndSpeed);
        run("cycle route uses bike lane factor and cycling speed", NavigatorTest::cycleRouteUsesBikeLaneFactorAndSpeed);
        run("public transport includes waiting time", NavigatorTest::publicTransportIncludesWaitingTime);
        run("navigator delegates to its strategy", NavigatorTest::navigatorDelegatesToItsStrategy);
        run("strategy can be switched at runtime", NavigatorTest::strategyCanBeSwitchedAtRuntime);
        run("new strategy works without changing navigator", NavigatorTest::newStrategyWorksWithoutChangingNavigator);
        run("null strategy is rejected", NavigatorTest::nullStrategyIsRejected);

        if (failures > 0) {
            System.out.println(failures + " test(s) failed");
            System.exit(1);
        }
        System.out.println("All tests passed");
    }

    private static void carRouteUsesRoadDetourAndSpeed() {
        Route route = new CarStrategy().buildRoute(START, DESTINATION);
        assertEquals(6.5, route.distanceKm());   // 5 * 1.3
        assertEquals(8L, route.durationMinutes()); // 6.5 / 50 h = 7.8 min
    }

    private static void walkRouteUsesPedestrianFactorAndSpeed() {
        Route route = new WalkStrategy().buildRoute(START, DESTINATION);
        assertEquals(5.5, route.distanceKm());   // 5 * 1.1
        assertEquals(66L, route.durationMinutes()); // 5.5 / 5 h = 66 min
    }

    private static void cycleRouteUsesBikeLaneFactorAndSpeed() {
        Route route = new CycleStrategy().buildRoute(START, DESTINATION);
        assertEquals(5.75, route.distanceKm());  // 5 * 1.15
        assertEquals(23L, route.durationMinutes()); // 5.75 / 15 h = 23 min
    }

    private static void publicTransportIncludesWaitingTime() {
        Route route = new PublicTransportStrategy().buildRoute(START, DESTINATION);
        assertEquals(7.0, route.distanceKm());   // 5 * 1.4
        assertEquals(24L, route.durationMinutes()); // 7 wait + 7 / 25 h = 16.8 -> 17
    }

    private static void navigatorDelegatesToItsStrategy() {
        Navigator navigator = new Navigator(new CarStrategy());
        assertEquals("Car", navigator.planRoute(START, DESTINATION).travelMode());
    }

    private static void strategyCanBeSwitchedAtRuntime() {
        Navigator navigator = new Navigator(new CarStrategy());
        navigator.setStrategy(new WalkStrategy());
        assertEquals("Walk", navigator.planRoute(START, DESTINATION).travelMode());
    }

    private static void newStrategyWorksWithoutChangingNavigator() {
        RouteStrategy teleport = (start, destination) ->
                new Route("Teleport", 0, 0, java.util.List.of("Blink"));
        Navigator navigator = new Navigator(teleport);
        assertEquals("Teleport", navigator.planRoute(START, DESTINATION).travelMode());
    }

    private static void nullStrategyIsRejected() {
        try {
            new Navigator(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new AssertionError("Expected NullPointerException");
    }

    // ---- tiny test helpers ----

    private static void run(String name, Runnable test) {
        try {
            test.run();
            System.out.println("PASS  " + name);
        } catch (AssertionError | RuntimeException e) {
            failures++;
            System.out.println("FAIL  " + name + " -> " + e.getMessage());
        }
    }

    private static void assertEquals(double expected, double actual) {
        if (Math.abs(expected - actual) > 1e-9) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(long expected, long actual) {
        if (expected != actual) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }

    private static void assertEquals(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("expected " + expected + " but was " + actual);
        }
    }
}
