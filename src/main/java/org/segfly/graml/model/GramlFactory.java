package org.segfly.graml.model;

import java.util.Map;

import org.segfly.graml.GramlException;

/**
 * The {@link GramlFactory} serves as a dependency-injection factory for Graml. Rather than using a DI framework and
 * bloat Graml's dependency graph, an implementation of this interface should manually wire classes as Graml's DI needs
 * are minimal.
 *
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public interface GramlFactory {

    /**
     * Creates a top-level object representing the Graml graph section wired with dependencies.
     *
     * @param fullMap
     * @param classmap
     * @param vertexProps
     * @param edgeProps
     * @return
     * @throws GramlException
     */
    public GraphSection getGraphSection(@SuppressWarnings("rawtypes") Map fullMap, ClassmapSection classmap,
            final VerticesSection vertexProps, final EdgesSection edgeProps) throws GramlException;

    /**
     * Creates a section-parser for the Graml header section.
     *
     * @param fullMap
     * @return
     * @throws GramlException
     */
    public GramlHeaderSection getGramlHeaderSection(@SuppressWarnings("rawtypes") Map fullMap) throws GramlException;

    /**
     * Creates a section-parser for the Graml classmap section.
     *
     * @param fullMap
     * @return
     * @throws GramlException
     */
    public ClassmapSection getClassmapSection(@SuppressWarnings("rawtypes") Map fullMap) throws GramlException;

    /**
     * Creates a section-parser for the Graml vertex properties section.
     *
     * @param fullMap
     * @return
     * @throws GramlException
     */
    public VerticesSection getVerticesSection(@SuppressWarnings("rawtypes") Map fullMap) throws GramlException;

    /**
     * Creates a section-parser for the Graml edge properties section.
     *
     * @param fullMap
     * @return
     * @throws GramlException
     */
    public EdgesSection getEdgesSection(@SuppressWarnings("rawtypes") Map fullMap) throws GramlException;
}