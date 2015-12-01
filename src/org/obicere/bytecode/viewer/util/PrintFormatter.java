package org.obicere.bytecode.viewer.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Applies a separate, platform-independent formatting scheme for logging.
 * This ensures that all format messages will be relatively similar. Also,
 * I didn't like the way the default logger worked, by using two lines.
 * This is why we can't have nice things.
 * <p>
 * The format achieved through this formatter is:
 * <p>
 * <code>Month/Day/Year Hour:Minutes:Seconds <i>level</i>:
 * <i>message</i></code>
 * <p>
 * Such like:
 * <p>
 * <code>01/01/1970 00:00:00 INFO: Unix timestamp starting!</code>
 *
 * @author Obicere
 */
public class PrintFormatter extends Formatter {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    // Meant to format: Month/Day/Year Hour:Minutes:Seconds
    private static final DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    /**
     * {@inheritDoc}
     */

    @Override
    public String format(final LogRecord record) {
        final StringBuilder message = new StringBuilder();

        message.append(SIMPLE_DATE_FORMAT.format(new Date(record.getMillis())));
        message.append(" ");
        message.append(record.getLevel().getLocalizedName());
        message.append(": ");
        message.append(formatMessage(record));
        message.append(LINE_SEPARATOR);

        if (record.getThrown() != null) {
            try {
                final StringWriter sw = new StringWriter();
                final PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                message.append(sw);
            } catch (final Exception ignored) {
            }
        }

        return message.toString();
    }
}