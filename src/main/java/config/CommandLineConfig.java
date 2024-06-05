package config;

import org.apache.commons.cli.*;

/**
 * This class is responsible for setting up the command line parser.
 * This way, we're able to read all the options and arguments that
 * have been passed on the commandline while running the application.
 */
public class CommandLineConfig {
    private final CommandLineParser parser;
    private final HelpFormatter formatter;
    private final Options options;
    private final String[] args;

    /**
     * Constructor to instantiate the Command Line Configurator.
     * @param optionsConfig - The options that we're expecting to parse from the command line.
     * @param args - Any other arguments that may be passed.
     */
    public CommandLineConfig(final OptionsConfig optionsConfig, final String[] args) {
        this.parser = new DefaultParser();
        this.formatter = new HelpFormatter();
        this.options = optionsConfig.getOptions();
        this.args = args;
    }

    /**
     * A Getter to return the command line parser.
     * This is needed so that we can extract the options and
     * their corresponding values and process them.
     * @return - The command line parser
     * @throws ParseException in case of any exceptions while parsing the options.
     */
    public CommandLine getCommandLine() throws ParseException {
        return this.parser.parse(options, args);
    }

    /**
     * This method is responsible for printing the help message in case of any exceptions.
     * @param helpMessage - The command line syntax.
     */
    public void printHelp(final String helpMessage) {
        this.formatter.printHelp(helpMessage, this.options);
    }
}
