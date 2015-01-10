package org.segfly.graml.model.impl;

import java.util.Map;

import org.segfly.graml.GramlException;
import org.segfly.graml.model.VerticesSection;

import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public class VerticesSectionImpl extends ElementSectionImpl implements VerticesSection {

    private Map<String, Map<String, Object>> properties;

    public VerticesSectionImpl(final Map<String, Map<String, Object>> section) throws GramlException {
        super(section);
    }

    @Override
    public void updateVertexProperties(final String vertexName, final Vertex vertex) {
        updateElementProperties(vertexName, vertex);
    }
}
