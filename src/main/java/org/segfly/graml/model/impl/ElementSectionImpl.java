package org.segfly.graml.model.impl;

import java.util.HashMap;
import java.util.Map;

import com.tinkerpop.blueprints.Element;

/**
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public abstract class ElementSectionImpl {

    private Map<String, Map<String, Object>> properties;

    protected ElementSectionImpl(final Map<String, Map<String, Object>> section) {
        if (section != null) {
            properties = section;
        } else {
            properties = new HashMap<String, Map<String, Object>>();
        }
    }

    public void updateElementProperties(final String elementName, final Element element) {
        Map<String, Object> edgeProperties = properties.get(elementName);
        if (edgeProperties != null) {
            edgeProperties.forEach((k, v) -> {
                element.setProperty(k, v);
            });
        }
    }
}