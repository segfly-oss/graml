package org.segfly.graml.model;

import com.tinkerpop.blueprints.Graph;

/**
 * The graph section of a Graml file specifies vertex to vertex mapping with edges. An implementation of this interface
 * contains the parsing and behavioral rules for this section within a Graml file but also any logic to support
 * database-specific features.
 *
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public interface GraphSection {

    /** The default name of a graph section within a Graml file. */
    public static String GRAPH_SECTION = "graph";

    /**
     * Once a {@link GraphSection} has been instantiated, the graph that exists in the Graml file can be injected into
     * any existing Tinkerpop Blueprints {@link Graph}. Implementations of this method may be database-vendor specific.
     *
     * @param target
     */
    void inject(Graph target);
}
