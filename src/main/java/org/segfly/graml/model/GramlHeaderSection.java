package org.segfly.graml.model;

/**
 * The graml section of a Graml file specifies the metadata about the Graml file. An implementation of this interface
 * contains the parsing and behavioral rules for this section within a Graml file.
 *
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public interface GramlHeaderSection {

    /** The default name of a graml section within a Graml file. */
    public static String GRAML_HEADER_SECTION = "graml";

    /** The name of the version property in the graml header. */
    public static String GRAML_VERSION_FIELD  = "version";

    /** The current version of the Graml specification implemented. */
    public static float  GRAML_VERSION        = 1.0f;
}
