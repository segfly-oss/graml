package org.segfly.graml.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.segfly.graml.GramlException;
import org.segfly.graml.model.ClassmapSection;

import static java.lang.String.format;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public class ClassmapSectionImpl implements ClassmapSection {
    private Map<String, String> classmap;
    private String              defaultVertexClass;
    private String              defaultEdgeClass;

    // TODO figure out why Map<String, ?> results in internal groovy compiler error
    public ClassmapSectionImpl(final Map<String, Object> section) throws GramlException {
        classmap = new HashMap<String, String>();

        // Pivot the map entries if the classmap section is present
        if (section != null) {
            for (String key : section.keySet()) {
                pivot(classmap, key, section.get(key));
            }
        }
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
     * @throws GramlException
     */
    @SuppressWarnings("unchecked")
    private void pivot(final Map<String, String> target, final String key, final Object value) throws GramlException {
        // Capture the defaults subsection
        if (CLASSMAP_DEFAULT_SUBSECTION.equals(key)) {
            Map<String, String> defaults = (Map<String, String>) value;
            defaultVertexClass = defaults.get(CLASSMAP_DEFAULT_VERTEX);
            defaultEdgeClass = defaults.get(CLASSMAP_DEFAULT_EDGE);
            return;
        }

        try {
            // For every value in the list create a key and map to the classname
            if (value instanceof List) {
                ((List<String>) value).forEach((it) -> target.put(it, key));
            } else {
                target.put((String) value, key);
            }
        } catch (ClassCastException e) {
            throw new GramlException("Invalid graml structure.", e);
        }
    }

    @Override
    public String resolveEdge(final String edgeName) {
        // If there was no classname and no default, just return the name as-is
        String className = classmap.getOrDefault(edgeName, defaultEdgeClass);
        if (className != null) {
            return format("%s:%s", className, edgeName);
        } else {
            return edgeName;
        }
    }

    @Override
    public String resolveVertex(final String vertexName) {
        // If there was no classname and no default, just return the name as-is
        String className = classmap.getOrDefault(vertexName, defaultVertexClass);
        if (className != null) {
            return format("%s:%s", className, vertexName);
        } else {
            return vertexName;
        }
    }

    @Override
    public String get(final String entity) {
        return classmap.get(entity);
    }
}
