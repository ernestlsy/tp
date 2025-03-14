package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.IdentifierPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;

/**
 * Updates the {@code Status} of a {@code Person} identified using the specified contact identifier
 */
public class UpdateCommand extends Command {
    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the application status of the person "
            + "identified by the specified contact identifier (name, email, or phone).\n"
            + "Parameters: IDENTIFIER_TYPE/KEYWORD [--custom] STATUS\n"
            + "Example: " + COMMAND_WORD + " n/Alex Yeoh";

    public static final String MESSAGE_NO_MATCHES = "No person matches provided keyword!";
    public static final String MESSAGE_MULTIPLE_MATCHES = "%1$d persons matched keyword. Please be more specific!";

    private static final String MESSAGE_UPDATE_STATUS_SUCCESS = "Updated status of: %1$s";

    private final IdentifierPredicate predicate;
    private final Status status;

    /**
     * @param predicate     The predicate used to identify the {@code Person} to be updated.
     * @param status        The {@code Status} to which the {@code Person}'s status should be set to
     */
    public UpdateCommand(IdentifierPredicate predicate, Status status) {
        this.predicate = predicate;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredPersonList(predicate);
        int numberOfMatches = model.getFilteredPersonListSize();
        if (numberOfMatches == 0) {
            return new CommandResult(String.format(MESSAGE_NO_MATCHES));
        } else if (numberOfMatches > 1) {
            return new CommandResult(String.format(MESSAGE_MULTIPLE_MATCHES, numberOfMatches));
        }
        Person target = model.getFilteredPersonList().get(0);
        Person updatedPerson = model.setStatus(target, status);
        return new CommandResult(String.format(MESSAGE_UPDATE_STATUS_SUCCESS, Messages.format(updatedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand otherUpdateCommand)) {
            return false;
        }

        return predicate.equals(otherUpdateCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("status", status)
                .toString();
    }
}
