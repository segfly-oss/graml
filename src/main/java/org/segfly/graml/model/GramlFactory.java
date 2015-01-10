package org.segfly.graml.model;

import java.util.Map;

import org.segfly.graml.GramlException;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public interface GramlFactory {
    ClassmapSection getClassmapSection(@SuppressWarnings("rawtypes") Map fullMap) throws GramlException;

    VerticesSection getVerticesSection(@SuppressWarnings("rawtypes") Map fullMap) throws GramlException;

    EdgesSection getEdgesSection(@SuppressWarnings("rawtypes") Map fullMap) throws GramlException;

    GraphSection getGraphSection(@SuppressWarnings("rawtypes") Map fullMap, ClassmapSection classmap,
            final VerticesSection vertexProps, final EdgesSection edgeProps) throws GramlException;
}