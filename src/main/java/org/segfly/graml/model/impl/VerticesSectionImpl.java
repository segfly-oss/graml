package org.segfly.graml.model.impl;

import java.util.HashMap;
import java.util.Map;

import org.segfly.graml.GramlException;
import org.segfly.graml.model.VerticesSection;

import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public class VerticesSectionImpl implements VerticesSection {

    private Map<String, Map<String, Object>> properties;

    public VerticesSectionImpl(final Map<String, Map<String, Object>> section) throws GramlException {
        if (section != null) {
            properties = section;
        } else {
            properties = new HashMap<String, Map<String, Object>>();
        }
    }

    @Override
    public void updateVertexProperties(final String vertexName, final Vertex vertex) {
        Map<String, Object> vertexProperties = properties.get(vertexName);
        if (vertexProperties != null) {
            vertexProperties.forEach((k, v) -> {
                vertex.setProperty(k, v);
            });
        }
    }
}
