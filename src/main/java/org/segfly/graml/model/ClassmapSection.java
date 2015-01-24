package org.segfly.graml.model;

/**
 * The classmap section of a Graml file specifies the mapping of edges and vertices to classes. This is relevant for
 * object-oriented graph databases such as OrientDB. An implementation of this interface contains the parsing and
 * behavioral rules for this section within a Graml file but also the name formatting as required by a specific database
 * implementation.
 *
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public interface ClassmapSection {

    /** The default name of a classmapping section within a Graml file. */
    public static String CLASSMAP_SECTION            = "classmap";

    /** The default name of the class-mapping-default values. */
    public static String CLASSMAP_DEFAULT_SUBSECTION = "defaults";

    /** The default name of the specific mappings for vertices. */
    public static String CLASSMAP_DEFAULT_VERTEX     = "vertex";

    /** The default name of the specific mappings for edges. */
    public static String CLASSMAP_DEFAULT_EDGE       = "edge";

    /**
     * Returns the class name for the given vertex name in a format as determined by the implementation.
     *
     * @param vertexName
     * @return
     */
    String resolveVertex(String vertexName);

    /**
     * Returns the class name for the given edge name in a format as determined by the implementation.
     *
     * @param edgeName
     * @return
     */
    String resolveEdge(String edgeName);

    /**
     * Simply returns the class name. Useful for map operations.
     *
     * @param entity
     * @return
     */
    String get(final String entity);
}
