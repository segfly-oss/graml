package org.segfly.graml.model.impl;

import java.util.Map;

import org.segfly.graml.GramlException;
import org.segfly.graml.model.EdgesSection;

import com.tinkerpop.blueprints.Edge;

/**
 * Default {@link EdgesSection} implementation that targets OrientDB usage.
 *
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public class EdgesSectionImpl extends ElementSectionImpl implements EdgesSection {

    /**
     * Instantiate this class with a map constrained to the edge properties section.
     *
     * @see ElementSectionImpl#ElementSectionImpl(Map)
     * @param section
     * @throws GramlException
     */
    public EdgesSectionImpl(final Map<String, Map<String, Object>> section) throws GramlException {
        super(section);
    }

    /**
     * @see org.segfly.graml.model.EdgesSection#updateEdgeProperties(java.lang.String, com.tinkerpop.blueprints.Edge)
     */
    @Override
    public void updateEdgeProperties(final String edgeName, final Edge edge) {
        updateElementProperties(edgeName, edge);
    }
}
