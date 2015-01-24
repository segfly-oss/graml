package org.segfly.graml.model;

import com.tinkerpop.blueprints.Vertex;

/**
 * The vertices section of a Graml file specifies additional vertex properties. An implementation of this interface
 * contains the parsing and behavioral rules for this section within a Graml file and may ignore or throw an exception
 * when encountering the section if the underlying database does not support edges with properties.
 *
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public interface VerticesSection {

    /** The default name of an vertices section within a Graml file. */
    public static String VERTICES_SECTION = "vertices";

    /**
     * Takes an vertex name and applies properties specified in Graml to the given vertex object.
     *
     * @param edgeName
     * @param edge
     */
    void updateVertexProperties(String vertexName, Vertex vertex);
}
