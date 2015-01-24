package org.segfly.graml.model.impl;

import java.util.HashMap;
import java.util.Map;

import com.tinkerpop.blueprints.Element;

/**
 * An abstract class used for handling properties of various element types such as vertices and edges.
 *
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public abstract class ElementSectionImpl {

    /** The mapping of properties to a given element */
    private Map<String, Map<String, Object>> properties;

    /**
     * Subclass constructor to initialize the mapping of properties to elements or vertices. If the supplied section is
     * null, a default empty mapping will be created.
     *
     * @param section
     */
    protected ElementSectionImpl(final Map<String, Map<String, Object>> section) {
        if (section != null) {
            properties = section;
        } else {
            properties = new HashMap<String, Map<String, Object>>();
        }
    }

    /**
     * Updates the given element with properties found in the parent classes's Graml section.
     *
     * @param elementName
     * @param element
     */
    public void updateElementProperties(final String elementName, final Element element) {
        Map<String, Object> edgeProperties = properties.get(elementName);
        if (edgeProperties != null) {
            edgeProperties.forEach((k, v) -> {
                element.setProperty(k, v);
            });
        }
    }
}