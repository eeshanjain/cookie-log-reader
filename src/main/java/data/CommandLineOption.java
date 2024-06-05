package data;

import java.util.List;

/**
 * This is a POJO to create some options that we can then add in {@link config.OptionsConfig}.
 */
public class CommandLineOption {
    private String option;
    private String longOption;
    private boolean hasArgs;
    private String description;
    private boolean required;

    public String getOption() {
        return option;
    }

    public void setOption(final String option) {
        this.option = option;
    }

    public String getLongOption() {
        return longOption;
    }

    public void setLongOption(final String longOption) {
        this.longOption = longOption;
    }

    public boolean isHasArgs() {
        return hasArgs;
    }

    public void setHasArgs(final boolean hasArgs) {
        this.hasArgs = hasArgs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    /**
     * This is a method that has been created for the purposes of testing the features.
     * In a real world scenario, considering that we're using the Spring framework,
     * we would ideally be using application.properties to create some default options.
     *
     * @return - List of default {@link CommandLineOption} options.
     */
    public static List<CommandLineOption> getDefaultOptions() {
        final CommandLineOption fileOption = new CommandLineOption();
        fileOption.setOption("f");
        fileOption.setLongOption("file");
        fileOption.setHasArgs(true);
        fileOption.setDescription("file path");
        fileOption.setRequired(true);

        final CommandLineOption dateOption = new CommandLineOption();
        dateOption.setOption("d");
        dateOption.setLongOption("date");
        dateOption.setHasArgs(true);
        dateOption.setDescription("date in format yyyy-MM-dd");
        dateOption.setRequired(true);

        return List.of(fileOption, dateOption);
    }
}
