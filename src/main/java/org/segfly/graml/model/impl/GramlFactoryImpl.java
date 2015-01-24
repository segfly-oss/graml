package org.segfly.graml.model.impl;

import java.util.Map;

import org.segfly.graml.GramlException;
import org.segfly.graml.model.ClassmapSection;
import org.segfly.graml.model.EdgesSection;
import org.segfly.graml.model.GramlFactory;
import org.segfly.graml.model.GramlHeaderSection;
import org.segfly.graml.model.GraphSection;
import org.segfly.graml.model.VerticesSection;

import static org.segfly.graml.model.ClassmapSection.CLASSMAP_SECTION;
import static org.segfly.graml.model.EdgesSection.EDGES_SECTION;
import static org.segfly.graml.model.GramlHeaderSection.GRAML_HEADER_SECTION;
import static org.segfly.graml.model.GraphSection.GRAPH_SECTION;
import static org.segfly.graml.model.VerticesSection.VERTICES_SECTION;

/**
 * Default implementation of {@link GramlFactory}.
 *
 * @see GramlFactory
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public class GramlFactoryImpl implements GramlFactory {
    /**
     * @see org.segfly.graml.model.GramlFactory#getGraphSection(java.util.Map, org.segfly.graml.model.ClassmapSection,
     *      org.segfly.graml.model.VerticesSection, org.segfly.graml.model.EdgesSection)
     */
    @SuppressWarnings("unchecked")
    @Override
    public GraphSection getGraphSection(@SuppressWarnings("rawtypes") final Map fullMap,
            final ClassmapSection classmap, final VerticesSection vertexProps, final EdgesSection edgeProps)
                    throws GramlException {
        return new GraphSectionImpl((Map<String, Map<String, Object>>) fullMap.get(GRAPH_SECTION), classmap,
                vertexProps, edgeProps);
    }

    /**
     * @see org.segfly.graml.model.GramlFactory#getGramlHeaderSection(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public GramlHeaderSection getGramlHeaderSection(@SuppressWarnings("rawtypes") final Map fullMap)
            throws GramlException {
        return new GramlHeaderSectionImpl((Map<String, Object>) fullMap.get(GRAML_HEADER_SECTION));
    }

    /**
     * @see org.segfly.graml.model.GramlFactory#getClassmapSection(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ClassmapSection getClassmapSection(@SuppressWarnings("rawtypes") final Map fullMap) throws GramlException {
        return new ClassmapSectionImpl((Map<String, Object>) fullMap.get(CLASSMAP_SECTION));
    }

    /**
     * @see org.segfly.graml.model.GramlFactory#getVerticesSection(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public VerticesSection getVerticesSection(@SuppressWarnings("rawtypes") final Map fullMap) throws GramlException {
        return new VerticesSectionImpl((Map<String, Map<String, Object>>) fullMap.get(VERTICES_SECTION));
    }

    /**
     * @see org.segfly.graml.model.GramlFactory#getEdgesSection(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public EdgesSection getEdgesSection(@SuppressWarnings("rawtypes") final Map fullMap) throws GramlException {
        return new EdgesSectionImpl((Map<String, Map<String, Object>>) fullMap.get(EDGES_SECTION));
    }
}