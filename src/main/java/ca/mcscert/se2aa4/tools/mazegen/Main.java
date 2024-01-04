package ca.mcscert.se2aa4.tools.mazegen;

import java.io.IOException;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Generating Maze");
        Configuration config = Configuration.load(args);
        Random random = buildReproducibleRandomGenerator(config);
        Maze theMaze = new Maze(config.width(), config.height());
        theMaze.carve(random);
        try {
            theMaze.export(config.out(), config.humanReadable());
        } catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
        }
        logger.info("End of generation");
    }

    private static Random buildReproducibleRandomGenerator(Configuration config) {
        Random random = new Random();
        random.setSeed(config.seed());
        return random;
    }

}
