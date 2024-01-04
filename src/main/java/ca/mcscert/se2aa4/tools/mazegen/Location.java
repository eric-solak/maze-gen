package ca.mcscert.se2aa4.tools.mazegen;

import java.util.Set;

public record Location(int x, int y) {

    public Set<Location> neighbors() {
        Location north = new Location(x(), y() + 2);
        Location east  = new Location(x() + 2, y());
        Location south = new Location(x(), y() - 2);
        Location west  = new Location(x() - 2, y());
        return Set.of(north, east, south, west);
    }

    @Override
    public String toString() { return "(" + x + "," + y + ')'; }

}