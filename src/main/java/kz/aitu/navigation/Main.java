package kz.aitu.navigation;

import java.util.LinkedHashMap;
import java.util.Map;

import kz.aitu.navigation.model.Location;
import kz.aitu.navigation.strategy.CarStrategy;
import kz.aitu.navigation.strategy.CycleStrategy;
import kz.aitu.navigation.strategy.PublicTransportStrategy;
import kz.aitu.navigation.strategy.RouteStrategy;
import kz.aitu.navigation.strategy.WalkStrategy;

/**
 * Client: chooses and configures the strategies, then switches them at runtime.
 * Run with no arguments to see all modes, or pass mode names, e.g. "car walk".
 */
public class Main {

    public static void main(String[] args) {
        Map<String, RouteStrategy> availableModes = createAvailableModes();
        Location start = new Location("Astana IT University", 0, 0);
        Location destination = new Location("Khan Shatyr", 6, 4);

        Navigator navigator = new Navigator(availableModes.get("car"));
        System.out.println("Trip: " + start.name() + " -> " + destination.name());
        System.out.println();

        for (String modeName : requestedModes(args, availableModes)) {
            navigator.setStrategy(availableModes.get(modeName));
            System.out.print(navigator.planRoute(start, destination));
            System.out.println();
        }
    }

    private static Map<String, RouteStrategy> createAvailableModes() {
        Map<String, RouteStrategy> modes = new LinkedHashMap<>();
        modes.put("car", new CarStrategy());
        modes.put("walk", new WalkStrategy());
        modes.put("cycle", new CycleStrategy());
        modes.put("transit", new PublicTransportStrategy());
        return modes;
    }

    private static Iterable<String> requestedModes(String[] args, Map<String, RouteStrategy> available) {
        if (args.length == 0) {
            return available.keySet();
        }
        for (String name : args) {
            if (!available.containsKey(name)) {
                throw new IllegalArgumentException(
                        "Unknown mode '" + name + "'. Available: " + available.keySet());
            }
        }
        return java.util.List.of(args);
    }
}
