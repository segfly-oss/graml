package org.segfly.graml.model;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public interface ClassmapSection {

    public static String CLASSMAP_SECTION = "classmap";

    String resolveVertex(String vertexName);

    String resolveEdge(String edgeName);

    String get(final String entity);
}
