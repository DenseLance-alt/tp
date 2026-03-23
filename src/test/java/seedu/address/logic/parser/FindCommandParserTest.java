package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonMatchesFilterPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // No attribute filters
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        // Invalid preamble
        assertParseFailure(parser, " random n/alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // No valid attribute filters. Does not satisfy: at least 1 filter with both prefix and keyword
        assertParseFailure(parser, " n/ a/ t/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/ a/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/ t/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " a/ t/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " a/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Name keywords must be alphanumeric
        assertParseFailure(parser, " n/ali-ce", FindCommand.MESSAGE_INVALID_NAME_KEYWORD);
        assertParseFailure(parser, " n/ali/ce", FindCommand.MESSAGE_INVALID_NAME_KEYWORD);
        assertParseFailure(parser, " n/alex r/", FindCommand.MESSAGE_INVALID_NAME_KEYWORD);
        assertParseFailure(parser, " n/alex r/ ", FindCommand.MESSAGE_INVALID_NAME_KEYWORD);
        assertParseFailure(parser, " t/north a/ n/ r/ ", FindCommand.MESSAGE_INVALID_NAME_KEYWORD);

        // Tag keywords must be alphanumeric
        assertParseFailure(parser, " t/no-rth", FindCommand.MESSAGE_INVALID_TAG_KEYWORD);
        assertParseFailure(parser, " t/no/rth", FindCommand.MESSAGE_INVALID_TAG_KEYWORD);
        assertParseFailure(parser, " t/north r/", FindCommand.MESSAGE_INVALID_TAG_KEYWORD);
        assertParseFailure(parser, " t/ north r/", FindCommand.MESSAGE_INVALID_TAG_KEYWORD);
        assertParseFailure(parser, " t/north r/ a/ n/", FindCommand.MESSAGE_INVALID_TAG_KEYWORD);
        assertParseFailure(parser, " t/ north /roy", FindCommand.MESSAGE_INVALID_TAG_KEYWORD);

        // Invalid prefix
        assertParseFailure(parser, " r/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " r/roof ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // Duplicate prefix
        assertParseFailure(parser, " n/ n/  ", MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);
        assertParseFailure(parser, " n/ a/ t/ n/  ", MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);
        assertParseFailure(parser, " n/ a/ n/ t/  ", MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);
        assertParseFailure(parser, " n/ t/ a/ n/ ", MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);

        assertParseFailure(parser, " a/ a/  ", MESSAGE_DUPLICATE_FIELDS + PREFIX_ADDRESS);
        assertParseFailure(parser, " n/ a/ a/ t/ ", MESSAGE_DUPLICATE_FIELDS + PREFIX_ADDRESS);
        assertParseFailure(parser, " n/ t/ a/ a/ ", MESSAGE_DUPLICATE_FIELDS + PREFIX_ADDRESS);

        assertParseFailure(parser, " t/ t/  ", MESSAGE_DUPLICATE_FIELDS + PREFIX_TAG);
        assertParseFailure(parser, " a/ n/ t/ t/  ", MESSAGE_DUPLICATE_FIELDS + PREFIX_TAG);
        assertParseFailure(parser, " a/ t/ n/ t/ ", MESSAGE_DUPLICATE_FIELDS + PREFIX_TAG);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // Leading space to simulate user args after command word
        // No leading/trailing whitespace between keywords

        // One attribute filter
        // Name filter only
        FindCommand expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Arrays.asList("Alice", "Bob"), Collections.emptyList(), Collections.emptyList()));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);
        // Whitespaces between keywords
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t", expectedFindCommand);
        // Filter not applied if no keywords
        assertParseSuccess(parser, " n/Alice Bob a/ t/ ", expectedFindCommand);
        assertParseSuccess(parser, " n/Alice Bob t/ a/ ", expectedFindCommand);
        assertParseSuccess(parser, " n/Alice Bob a/  \t t/ \t  ", expectedFindCommand);
        assertParseSuccess(parser, " n/Alice Bob t/ \t a/ \t  ", expectedFindCommand);

        // Address filter only
        expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Collections.emptyList(), Arrays.asList("jurong", "clementi"), Collections.emptyList()));
        assertParseSuccess(parser, " a/jurong clementi", expectedFindCommand);
        // Whitespaces between keywords
        assertParseSuccess(parser, " a/ \n jurong \n \t clementi  \t", expectedFindCommand);
        // Filter not applied (empty list) if no keywords
        assertParseSuccess(parser, " n/ a/jurong clementi t/ ", expectedFindCommand);
        assertParseSuccess(parser, " t/ a/jurong clementi n/ ", expectedFindCommand);
        assertParseSuccess(parser, "  n/  \t a/jurong clementi \n t/ \t  ", expectedFindCommand);
        assertParseSuccess(parser, " a/jurong clementi \t n/ \t t/ \t  ", expectedFindCommand);

        expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Collections.emptyList(), Arrays.asList("#30-100"), Collections.emptyList()));
        assertParseSuccess(parser, " a/#30-100", expectedFindCommand);

        expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Collections.emptyList(), Arrays.asList("street", "#30-100"), Collections.emptyList()));
        assertParseSuccess(parser, " a/street #30-100", expectedFindCommand);

        // Tag filter only
        expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Collections.emptyList(), Collections.emptyList(), Arrays.asList("west", "east")));
        assertParseSuccess(parser, " t/west east", expectedFindCommand);
        // Whitespaces between keywords
        assertParseSuccess(parser, " t/ \n west \n \t east  \t", expectedFindCommand);
        // Filter not applied (empty list) if no keywords
        assertParseSuccess(parser, " n/ a/ t/west east ", expectedFindCommand);
        assertParseSuccess(parser, " a/ n/ t/west east ", expectedFindCommand);
        assertParseSuccess(parser, " n/ \t a/ \t t/west east ", expectedFindCommand);
        assertParseSuccess(parser, " t/west east \t a/ \t n/ \t  ", expectedFindCommand);

        // Two attribute filters
        expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Arrays.asList("Alice", "Bob"), Arrays.asList("Clementi", "Jurong"), Collections.emptyList()));
        assertParseSuccess(parser, " n/Alice Bob a/Clementi Jurong", expectedFindCommand);
        // Whitespaces between keywords and prefixes
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t "
                + "a/ \n Clementi \n \t Jurong  \t ", expectedFindCommand);
        // Prefix in any order
        assertParseSuccess(parser, " a/Clementi Jurong n/Alice Bob ", expectedFindCommand);

        expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Arrays.asList("Alice", "Bob"), Collections.emptyList(), Arrays.asList("Central", "West")));
        assertParseSuccess(parser, " n/Alice Bob t/Central West", expectedFindCommand);
        // Whitespaces between keywords and prefixes
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob  \t "
                + "t/ \n Central \n \t West  \t ", expectedFindCommand);
        // Prefix in any order
        assertParseSuccess(parser, " t/Central West n/Alice Bob ", expectedFindCommand);

        expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Collections.emptyList(), Arrays.asList("Clementi", "Jurong"), Arrays.asList("Central", "West")));
        assertParseSuccess(parser, " a/Clementi Jurong t/Central West", expectedFindCommand);
        // Whitespaces between keywords and prefixes
        assertParseSuccess(parser, " a/ \n Clementi \n \t Jurong  \t "
                + "t/ \n Central \n \t West  \t ", expectedFindCommand);
        // Prefix in any order
        assertParseSuccess(parser, " t/Central West a/Clementi Jurong ", expectedFindCommand);

        // Three attribute filters
        expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Arrays.asList("Alice", "Bob"), Arrays.asList("Clementi", "Jurong"), Arrays.asList("Central", "West")));
        assertParseSuccess(parser, " n/Alice Bob a/Clementi Jurong t/Central West", expectedFindCommand);
        // Whitespaces between keywords and prefixes
        assertParseSuccess(parser, " n/ \n Alice \n \t Bob \t a/ \n Clementi "
                + "\n \t Jurong  \t t/ \n Central \n \t West  \t ", expectedFindCommand);
        // Prefix in any order
        assertParseSuccess(parser, " t/Central West a/Clementi Jurong n/Alice Bob ", expectedFindCommand);
        assertParseSuccess(parser, " t/Central West n/Alice Bob a/Clementi Jurong ", expectedFindCommand);
        assertParseSuccess(parser, " a/Clementi Jurong t/Central West n/Alice Bob ", expectedFindCommand);

        // Address filter contain non-alphanumeric characters
        expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Arrays.asList("Alice", "Bob"), Arrays.asList("Blk", "r/myhouse"), Arrays.asList("Central", "West")));
        assertParseSuccess(parser, " n/Alice Bob a/Blk r/myhouse t/Central West", expectedFindCommand);

        expectedFindCommand = new FindCommand(new PersonMatchesFilterPredicate(
                Collections.emptyList(), Arrays.asList("r/"), Arrays.asList("Central", "West")));
        assertParseSuccess(parser, " t/Central West a/ r/ n/ ", expectedFindCommand);

    }

}
