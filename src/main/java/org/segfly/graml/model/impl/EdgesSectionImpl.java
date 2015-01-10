package org.segfly.graml.model.impl;

import java.util.HashMap;
import java.util.Map;

import org.segfly.graml.GramlException;
import org.segfly.graml.model.EdgesSection;

import com.tinkerpop.blueprints.Edge;

/**
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public class EdgesSectionImpl implements EdgesSection {

    private Map<String, Map<String, Object>> properties;

    public EdgesSectionImpl(final Map<String, Map<String, Object>> section) throws GramlException {
        if (section != null) {
            properties = section;
        } else {
            properties = new HashMap<String, Map<String, Object>>();
        }
    }

    @Override
    public void updateEdgeProperties(final String edgeName, final Edge edge) {
        Map<String, Object> edgeProperties = properties.get(edgeName);
        if (edgeProperties != null) {
            edgeProperties.forEach((k, v) -> {
                edge.setProperty(k, v);
            });
        }
    }
}
