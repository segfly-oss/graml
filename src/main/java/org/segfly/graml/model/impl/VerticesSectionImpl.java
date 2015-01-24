package org.segfly.graml.model.impl;

import java.util.Map;

import org.segfly.graml.GramlException;
import org.segfly.graml.model.EdgesSection;
import org.segfly.graml.model.VerticesSection;

import com.tinkerpop.blueprints.Vertex;

/**
 * Default {@link EdgesSection} implementation that targets OrientDB usage.
 *
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public class VerticesSectionImpl extends ElementSectionImpl implements VerticesSection {

    /**
     * Instantiate this class with a map constrained to the vertex properties section.
     *
     * @see ElementSectionImpl#ElementSectionImpl(Map)
     * @param section
     * @throws GramlException
     */
    public VerticesSectionImpl(final Map<String, Map<String, Object>> section) throws GramlException {
        super(section);
    }

    /**
     * Resolves the classname for the given edge in the format expected by OrientDB class:[classname]
     *
     * @see org.segfly.graml.model.ClassmapSection#resolveVertex(java.lang.String)
     */
    @Override
    public void updateVertexProperties(final String vertexName, final Vertex vertex) {
        updateElementProperties(vertexName, vertex);
    }
}
