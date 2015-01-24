package org.segfly.graml.model;

import com.tinkerpop.blueprints.Edge;

/**
 * The edges section of a Graml file specifies additional edge properties. An implementation of this interface contains
 * the parsing and behavioral rules for this section within a Graml file and may ignore or throw an exception when
 * encountering the section if the underlying database does not support edges with properties.
 *
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public interface EdgesSection {

    /** The default name of an edges section within a Graml file. */
    public static String EDGES_SECTION = "edges";

    /**
     * Takes an edge name and applies properties specified in Graml to the given edge object.
     *
     * @param edgeName
     * @param edge
     */
    void updateEdgeProperties(String edgeName, Edge edge);
}
