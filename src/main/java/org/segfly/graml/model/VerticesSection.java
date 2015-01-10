package org.segfly.graml.model;

import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nicholas Pace
 * @since Jan 9, 2015
 */
public interface VerticesSection {
    public static String VERTICES_SECTION = "vertices";

    void updateVertexProperties(String vertexName, Vertex vertex);
}
