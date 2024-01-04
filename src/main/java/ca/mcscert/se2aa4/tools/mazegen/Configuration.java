package ca.mcscert.se2aa4.tools.mazegen;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public record Configuration(int width, int height, BufferedWriter out,
                            long seed, boolean humanReadable) {

    public Configuration {
        if (width < 5)
            throw  new IllegalArgumentException("Width must be >= 5 (given: " + width + ")");
        if (width % 2 != 1)
            throw  new IllegalArgumentException("Width must be an odd integer (given: " + width + ")");
        if (height < 5)
            throw  new IllegalArgumentException("Height must be >= 5 (given: " + height + ")");
        if (height % 2 != 1)
            throw  new IllegalArgumentException("Height must be an odd integer (given: " + width + ")");
    }

    public static Configuration load(String[] args) {
        Options options = new Options();
        options.addOption("w", true, "width of the maze (must be odd), default 41");
        options.addOption("h", true, "height of the maze (must be odd), default 41");
        options.addOption("s", true, "Random seed value, default `now()`");
        options.addOption("o", true, "Output file, default stdout");
        options.addOption("human", false, "should the output be human readable?");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            int w = Integer.parseInt(cmd.getOptionValue('w', "41"));
            int h = Integer.parseInt(cmd.getOptionValue('h', "41"));
            long s = (cmd.hasOption('s')? Long.parseLong(cmd.getOptionValue('s')) : System.currentTimeMillis());
            BufferedWriter out = null;
            if(cmd.hasOption('o')) {
                out = new BufferedWriter(new FileWriter(cmd.getOptionValue('o')));
            } else {
                out = new BufferedWriter(new OutputStreamWriter(System.out));
            }
            boolean hu = cmd.hasOption("human");
            return new Configuration(w, h, out, s, hu);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}