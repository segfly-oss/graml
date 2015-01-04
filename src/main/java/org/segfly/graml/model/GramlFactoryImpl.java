package org.segfly.graml.model;

import java.util.Map;

import org.segfly.graml.GramlException;

import static org.segfly.graml.model.ClassmapSection.CLASSMAP_SECTION;
import static org.segfly.graml.model.GraphSection.GRAPH_SECTION;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public class GramlFactoryImpl implements GramlFactory {

    @SuppressWarnings("unchecked")
    @Override
    public ClassmapSection getClassmapSection(@SuppressWarnings("rawtypes") final Map rawmap) throws GramlException {
        return new ClassmapSectionImpl((Map<String, Object>) rawmap.get(CLASSMAP_SECTION));
    }

    @SuppressWarnings("unchecked")
    @Override
    public GraphSection getGraphSection(@SuppressWarnings("rawtypes") final Map rawmap, final ClassmapSection classmap)
            throws GramlException {
        return new GraphSectionImpl((Map<String, Map<String, Object>>) rawmap.get(GRAPH_SECTION), classmap);
    }
}
