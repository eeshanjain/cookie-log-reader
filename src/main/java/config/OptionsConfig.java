package config;

import data.CommandLineOption;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * This class is responsible for configuring the options that
 * we can expect to parse in the command line.
 */
public class OptionsConfig {
    private final Options options;

    /**
     * Constructor to instantiate the options.
     */
    public OptionsConfig() {
        options = new Options();
    }

    /**
     * Method to add an option as per the user's requirement.
     * @param opt - The option to be added
     */
    public void addOption(final CommandLineOption opt) {
        Option option = new Option(
                opt.getOption(),
                opt.getLongOption(),
                opt.isHasArgs(),
                opt.getDescription()
        );
        option.setRequired(opt.isRequired());
        this.options.addOption(option);
    }

    /**
     * Getter to return the options that have been added so far.
     * @return - All the options that have been added so far.
     */
    public Options getOptions() {
        return this.options;
    }
}
