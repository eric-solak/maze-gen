package ca.mcscert.se2aa4.tools.mazegen;

import java.io.BufferedWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MazeExporter {

    private static final Logger logger = LogManager.getLogger();

    private final Maze maze;

    public MazeExporter(Maze theMaze) {
        this.maze = theMaze;
    }

    public void export(BufferedWriter out, boolean humanReadable) throws IOException {
        logger.trace("Exporting the maze to a textual format");
        for (int i = 0; i < maze.getHeight(); i++ ) {
            for(int j = 0; j < maze.getWidth(); j++) {
                out.write(maze.tileAt(j,i).toString());
                if(humanReadable) // Double the printing to make it more readable
                    out.write(maze.tileAt(j,i).toString());
            }
            out.write(System.lineSeparator());
            out.flush();
        }
        out.flush();
        logger.trace("End of exportation");
    }

}
