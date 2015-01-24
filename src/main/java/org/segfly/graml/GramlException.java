package org.segfly.graml;

/**
 * Used when there is an exception in Graml processing or syntax.
 *
 * @author Nicholas Pace
 * @since Jan 3, 2015
 */
@SuppressWarnings("serial")
public class GramlException extends RuntimeException {

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     * @param msg
     * @param cause
     */
    public GramlException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     * @param msg
     */
    public GramlException(final String msg) {
        super(msg);
    }
}
