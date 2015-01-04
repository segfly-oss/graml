package org.segfly.graml;

/**
 * Used when there is an exception in Graml processing or syntax.
 *
 * @author Nicholas Pace
 * @since Jan 3, 2015
 */
@SuppressWarnings("serial")
public class GramlException extends Exception {

    public GramlException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    public GramlException(final String msg) {
        super(msg);
    }
}
