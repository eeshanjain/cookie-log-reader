import config.CommandLineConfig;
import config.OptionsConfig;
import data.CommandLineOption;
import org.apache.commons.cli.*;
import service.ICookieReader;
import service.impl.CookieReaderImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This is the main class that is responsible for running the Cookie analyzer
 * in order to print the most active cookies.
 */
public class CookieAnalyzer {

    private static final ICookieReader COOKIE_READER = new CookieReaderImpl();

    /**
     * This is the main method that we're using to run the analyzer.
     * I've decided to not use Spring because that would complicate things
     * unnecessarily and decided to go with a simpler approach.
     *
     * @param args - The arguments that are passed in the commandline.
     */
    public static void main(final String[] args) {
        analyzeCookies(args);
    }

    public static void analyzeCookies(final String[] args) {
        final OptionsConfig optionsConfig = new OptionsConfig();

        CommandLineOption
                .getDefaultOptions()
                .forEach(optionsConfig::addOption);

        final  CommandLineConfig cmdConfig = new CommandLineConfig(optionsConfig, args);
        try {
            final CommandLine cmd = cmdConfig.getCommandLine();
            final String filePath = cmd.getOptionValue("file");
            final String dateStr = cmd.getOptionValue("date");

            final List<String> mostActiveCookies = COOKIE_READER.findMostActiveCookies(filePath, dateStr);
            COOKIE_READER.processMostActiveCookies(mostActiveCookies);
        } catch (final ParseException e) {
            // In case of exceptions while parsing the command line.
            System.out.println(e.getMessage());
            cmdConfig.printHelp("cookie-analyzer");
        } catch (final IOException e) {
            // In case of exceptions while tring to access the cookie file.
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
