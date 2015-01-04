package org.segfly.graml.model;

import java.util.Map;

import org.segfly.graml.GramlException;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public interface GramlFactory {
    ClassmapSection getClassmapSection(@SuppressWarnings("rawtypes") Map rawmap) throws GramlException;

    GraphSection getGraphSection(@SuppressWarnings("rawtypes") Map rawmap, ClassmapSection classmap)
            throws GramlException;
}