package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Show the menu of the selected vendor.
 */
public class MenuCommand extends Command {

    public static final String COMMAND_WORD = "menu";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the menu of the selected vendor.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("Showing menu....", true);
    }
}
