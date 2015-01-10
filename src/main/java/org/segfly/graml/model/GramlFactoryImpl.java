package org.segfly.graml.model;

import java.util.Map;

import org.segfly.graml.GramlException;

import static org.segfly.graml.model.ClassmapSection.CLASSMAP_SECTION;
import static org.segfly.graml.model.EdgesSection.EDGES_SECTION;
import static org.segfly.graml.model.GraphSection.GRAPH_SECTION;
import static org.segfly.graml.model.VerticesSection.VERTICES_SECTION;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public class GramlFactoryImpl implements GramlFactory {

    @SuppressWarnings("unchecked")
    @Override
    public ClassmapSection getClassmapSection(@SuppressWarnings("rawtypes") final Map fullMap) throws GramlException {
        return new ClassmapSectionImpl((Map<String, Object>) fullMap.get(CLASSMAP_SECTION));
    }

    @SuppressWarnings("unchecked")
    @Override
    public VerticesSection getVerticesSection(@SuppressWarnings("rawtypes") final Map fullMap) throws GramlException {
        return new VerticesSectionImpl((Map<String, Map<String, Object>>) fullMap.get(VERTICES_SECTION));
    }

    @SuppressWarnings("unchecked")
    @Override
    public EdgesSection getEdgesSection(@SuppressWarnings("rawtypes") final Map fullMap) throws GramlException {
        return new EdgesSectionImpl((Map<String, Map<String, Object>>) fullMap.get(EDGES_SECTION));
    }

    @SuppressWarnings("unchecked")
    @Override
    public GraphSection getGraphSection(@SuppressWarnings("rawtypes") final Map fullMap,
            final ClassmapSection classmap, final VerticesSection vertexProps, final EdgesSection edgeProps)
                    throws GramlException {
        return new GraphSectionImpl((Map<String, Map<String, Object>>) fullMap.get(GRAPH_SECTION), classmap,
                vertexProps, edgeProps);
    }
}
