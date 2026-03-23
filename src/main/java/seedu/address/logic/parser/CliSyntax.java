package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for Person objects */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    /* Prefix definitions for Delivery objects */
    public static final Prefix PREFIX_START_DATE = new Prefix("d/");
    public static final Prefix PREFIX_END_DATE = new Prefix("e/");
    public static final Prefix PREFIX_NUMBER_OF_DAYS = new Prefix("n/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_DAYS = new Prefix("days/");
}
