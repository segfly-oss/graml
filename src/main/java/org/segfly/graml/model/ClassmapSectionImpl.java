package org.segfly.graml.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.segfly.graml.GramlException;

import static java.lang.String.format;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public class ClassmapSectionImpl implements ClassmapSection {
    private Map<String, String> classmap;

    // TODO figure out why Map<String, ?> results in internal groovy compiler error
    public ClassmapSectionImpl(final Map<String, Object> section) throws GramlException {
        if (section == null) {
            throw new GramlException("Missing required classmap section.");
        }

        // Pivot the map
        classmap = new HashMap<String, String>();
        section.forEach((k, v) -> pivot(classmap, k, v));
    }

    /**
     * Takes a {@link Map} whose values may or may not a {@link List} or individual {@link String}s and pivots the
     * values to keys in the target map.
     *
     * @param target
     *            The map to insert pivoted entries
     * @param key
     *            the incoming key
     * @param value
     *            a {@link List} of strings or an individual string
     */
    @SuppressWarnings("unchecked")
    private void pivot(final Map<String, String> target, final String key, final Object value) {
        if (value instanceof List) {
            ((List<String>) value).forEach((it) -> target.put(it, key));
        } else {
            target.put((String) value, key);
        }
    }

    @Override
    public String resolveEdge(final String edgeName) {
        return format("%s:%s", classmap.get(edgeName), edgeName);
    }

    @Override
    public String resolveVertex(final String vertexName) {
        return format("%s:%s", classmap.get(vertexName), vertexName);
    }

    @Override
    public String get(final String entity) {
        return classmap.get(entity);
    }
}
