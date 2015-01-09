package org.segfly.graml.model;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public interface ClassmapSection {

    public static String CLASSMAP_SECTION            = "classmap";
    public static String CLASSMAP_DEFAULT_SUBSECTION = "defaults";
    public static String CLASSMAP_DEFAULT_VERTEX     = "vertex";
    public static String CLASSMAP_DEFAULT_EDGE       = "edge";

    String resolveVertex(String vertexName);

    String resolveEdge(String edgeName);

    String get(final String entity);
}
