package org.segfly.graml.model.impl;

import java.util.Map;

import org.segfly.graml.GramlException;
import org.segfly.graml.model.EdgesSection;

import com.tinkerpop.blueprints.Edge;

/**
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public class EdgesSectionImpl extends ElementSectionImpl implements EdgesSection {

    Map<String, Map<String, Object>> properties;

    public EdgesSectionImpl(final Map<String, Map<String, Object>> section) throws GramlException {
        super(section);
    }

    @Override
    public void updateEdgeProperties(final String edgeName, final Edge edge) {
        updateElementProperties(edgeName, edge);
    }
}
