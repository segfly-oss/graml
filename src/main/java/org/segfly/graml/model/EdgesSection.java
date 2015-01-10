package org.segfly.graml.model;

import com.tinkerpop.blueprints.Edge;

/**
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public interface EdgesSection {
    public static String EDGES_SECTION = "edges";

    void updateEdgeProperties(String edgeName, Edge edge);
}
