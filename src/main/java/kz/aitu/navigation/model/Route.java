package kz.aitu.navigation.model;

import java.util.List;

/**
 * The result of route planning: how far, how long, and what to do along the way.
 */
public record Route(String travelMode, double distanceKm, long durationMinutes, List<String> steps) {

    public Route {
        steps = List.copyOf(steps);
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append(String.format("[%s] %.1f km, about %d min%n", travelMode, distanceKm, durationMinutes));
        for (int i = 0; i < steps.size(); i++) {
            text.append(String.format("  %d. %s%n", i + 1, steps.get(i)));
        }
        return text.toString();
    }
}
