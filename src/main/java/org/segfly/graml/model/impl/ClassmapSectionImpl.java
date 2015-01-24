package org.segfly.graml.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.segfly.graml.GramlException;
import org.segfly.graml.model.ClassmapSection;

import static java.lang.String.format;

/**
 * This is the default {@link ClassmapSection} implementation and targets OrientDB usage.
 *
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public class ClassmapSectionImpl implements ClassmapSection {
    private Map<String, String> classmap;
    private String              defaultVertexClass;
    private String              defaultEdgeClass;

    /**
     * Instantiate this class with a map constrained to the classmap section.
     *
     * @param section
     *            A map containing only key/values from the classmap section of a YAML file.
     */
    public ClassmapSectionImpl(final Map<String, Object> section) {
        // TODO figure out why Map<String, ?> results in internal groovy compiler error
        classmap = new HashMap<String, String>();

        // Pivot the map entries if the classmap section is present
        if (section != null) {
            section.forEach((k, v) -> pivot(classmap, k, v));
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
     */
    @SuppressWarnings("unchecked")
    private void pivot(final Map<String, String> target, final String key, final Object value) {
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

    /**
     * Resolves the classname for the given vertex in the format expected by OrientDB class:[classname]
     *
     * @see org.segfly.graml.model.ClassmapSection#resolveEdge(java.lang.String)
     */
    @Override
    public String resolveEdge(final String edgeName) {
        // If there was no classname and no default, just return the name as-is
        String className = classmap.getOrDefault(edgeName, defaultEdgeClass);
        if (className != null) {
            return format("class:%s", className);
        } else {
            return edgeName;
        }
    }

    /**
     * Resolves the classname for the given edge in the format expected by OrientDB class:[classname]
     *
     * @see org.segfly.graml.model.ClassmapSection#resolveVertex(java.lang.String)
     */
    @Override
    public String resolveVertex(final String vertexName) {
        // If there was no classname and no default, just return the name as-is
        String className = classmap.getOrDefault(vertexName, defaultVertexClass);
        if (className != null) {
            return format("class:%s", className);
        } else {
            return vertexName;
        }
    }

    /**
     * @see org.segfly.graml.model.ClassmapSection#get(java.lang.String)
     */
    @Override
    public String get(final String entity) {
        return classmap.get(entity);
    }
}
