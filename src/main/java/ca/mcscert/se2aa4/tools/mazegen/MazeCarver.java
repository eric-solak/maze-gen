package ca.mcscert.se2aa4.tools.mazegen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class MazeCarver {

    public void carve(Maze theMaze, Random random) {
        Location start = new Location(1, 1);
        Set<Location> visited = new HashSet<>();
        visited.add(start);
        Set<Location> frontier = new HashSet<>(theMaze.neighborsInRange(start));
        while (!frontier.isEmpty()) {
            Location next = getRandomElement(frontier, random);
            connect(theMaze, next, visited);
            frontier.remove(next);
            visited.add(next);
            frontier.addAll(theMaze.neighborsInRange(next).stream()
                    .filter(loc -> ! visited.contains(loc)).collect(Collectors.toSet()));
        }
        createEntry(theMaze, random);
        createExit(theMaze, random);
    }

    private void connect(Maze theMaze, Location dangling, Set<Location> visited) {
        Set<Location> candidates = theMaze.neighborsInRange(dangling);
        candidates.retainAll(visited);
        Location hook = candidates.stream().toList().get(0);
        theMaze.destroyWall(hook, dangling);
    }

    private Location getRandomElement(Set<Location> values, Random r) {
        List<Location> lst = new ArrayList<>(values.stream().toList());
        Collections.shuffle(lst, r);
        return lst.get(0);
    }

    private void createEntry(Maze theMaze, Random random) {
        List<Location> candidates = new LinkedList<>();
        // Opening on the left hand side (x = 1)
        for (int y = 1; y < theMaze.getHeight() - 1; y++) {
            if(theMaze.tileAt(1, y) == Tile.EMPTY)
                candidates.add(new Location(1, y));
        }
        Collections.shuffle(candidates, random);
        Location entry = candidates.get(0);
        theMaze.punch(new Location(0, entry.y()));

    }

    private void createExit(Maze theMaze, Random random) {
        List<Location> candidates = new LinkedList<>();
        // Opening on the left hand side (x = 1)
        for (int y = 1; y < theMaze.getHeight() - 1; y++) {
            if(theMaze.tileAt(theMaze.getWidth() - 2, y) == Tile.EMPTY)
                candidates.add(new Location(1, y));
        }
        Collections.shuffle(candidates, random);
        Location exit = candidates.get(0);
        theMaze.punch(new Location(theMaze.getWidth() - 1, exit.y()));
    }

}
