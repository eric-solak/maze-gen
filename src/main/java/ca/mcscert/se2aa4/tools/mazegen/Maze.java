package ca.mcscert.se2aa4.tools.mazegen;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Maze {

    private static final Logger logger = LogManager.getLogger();

    private final Tile[][] theGrid;

    public Maze(int width, int height) {
        this.theGrid = new Tile[height][width];
        fillWithWalls();
    }

    public int getWidth() { return this.theGrid[0].length; }

    public int getHeight() { return this.theGrid.length; }

    public Tile tileAt(int x, int y) { return theGrid[y][x]; }

    public void carve(Random random) {
        MazeCarver carver = new MazeCarver();
        carver.carve(this, random);
    }

    public void export(BufferedWriter out, boolean humanReadable) throws IOException {
        MazeExporter exporter = new MazeExporter(this);
        exporter.export(out, humanReadable);
    }


    public void punch(Location loc) { this.theGrid[loc.y()][loc.x()] = Tile.EMPTY; }

    public void destroyWall(Location from, Location to) {
        logger.trace("Carving tunnel rom " + from + " to " + to);
        punch(from);
        punch(to);
        int deltaX = 0;
        if (to.x() - from.x() < 0) { deltaX = -1; } else if (to.x() - from.x() > 0) { deltaX = 1; }
        int deltaY = 0;
        if (to.y() - from.y() < 0) { deltaY = -1; } else if (to.y() - from.y() > 0) { deltaY = 1; }
        Location tunnel = new Location(from.x() + deltaX, from.y() + deltaY);
        punch(tunnel);
    }

    public Set<Location> neighborsInRange(Location loc) {
        return loc.neighbors().stream()
                .filter(l -> l.x() > 0 && l.x() < getWidth() - 1)
                .filter(l -> l.y() > 0 && l.y() < getHeight() - 1)
                .collect(Collectors.toSet());
    }

    private void fillWithWalls() {
        for(int i = 0; i < theGrid.length; i++) {
            Tile[] row = theGrid[i];
            for(int j = 0; j < row.length; j++) {
                theGrid[i][j] = Tile.WALL;
            }
        }
    }

}
