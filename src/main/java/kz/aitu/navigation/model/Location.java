package kz.aitu.navigation.model;

/**
 * A named point on a flat map. Coordinates are expressed in kilometres.
 */
public record Location(String name, double xKm, double yKm) {

    public double straightLineDistanceTo(Location other) {
        double dx = other.xKm - xKm;
        double dy = other.yKm - yKm;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
