package org.segfly.graml.model;

import com.tinkerpop.blueprints.Graph;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public interface GraphSection {

    public static String GRAPH_SECTION = "graph";

    void inject(Graph target);
}
