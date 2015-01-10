package org.segfly.graml.model.impl;

import java.util.Map;

import org.segfly.graml.GramlException;
import org.segfly.graml.model.GramlHeaderSection;

import static java.lang.String.format;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public class GramlHeaderSectionImpl implements GramlHeaderSection {

    // TODO figure out why Map<String, ?> results in internal groovy compiler error
    public GramlHeaderSectionImpl(final Map<String, Object> section) throws GramlException {
        if (section == null) {
            throw new GramlException("Missing required Graml header.");
        }

        if (section.get(GRAML_VERSION_FIELD) == null) {
            throw new GramlException("Missing Graml version.");
        }

        try {
            Number version = (Number) section.get(GRAML_VERSION_FIELD);
            if (GRAML_VERSION < version.floatValue()) {
                throw new GramlException(format("Unsupported Graml version: %s", version));
            }
        } catch (NumberFormatException | ClassCastException e) {
            throw new GramlException("Graml version identifier is not a number.", e);
        }
    }
}
