package org.segfly.graml.model.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LRUMap;
import org.segfly.graml.GramlException;
import org.segfly.graml.model.ClassmapSection;
import org.segfly.graml.model.EdgesSection;
import org.segfly.graml.model.GraphSection;
import org.segfly.graml.model.VerticesSection;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static java.lang.String.format;

/**
 * This is the default {@link GraphSection} implementation and targets OrientDB usage.
 *
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public class GraphSectionImpl implements GraphSection {
    /** The default name of a classmapping section within a Graml file. */
    public static final String               ELEMENT_NAME_PROPERTY = "name";

    private ClassmapSection                  classmap;
    private Map<String, Map<String, Object>> section;
    private LRUMap                           vertexCache;
    private VerticesSection                  vertexProps;
    private EdgesSection                     edgeProps;

    /**
     * Instantiate this class with a map constrained to the graph section.
     *
     * @param section
     * @param classmap
     * @param vertexProps
     * @param edgeProps
     */
    public GraphSectionImpl(final Map<String, Map<String, Object>> section, final ClassmapSection classmap,
            final VerticesSection vertexProps, final EdgesSection edgeProps) {
        // TODO figure out why Map<String, ?> results in internal groovy compiler error
        if (section == null) {
            throw new GramlException("Missing required graph section.");
        }

        this.classmap = classmap;
        this.vertexProps = vertexProps;
        this.edgeProps = edgeProps;
        this.section = section;
        vertexCache = new LRUMap();
    }

    /**
     * @see org.segfly.graml.model.GraphSection#inject(com.tinkerpop.blueprints.Graph)
     */
    @Override
    public void inject(final Graph target) {
        section.forEach((srcVertex, edges) -> injectSourceVertices(target, srcVertex, edges));
    }

    /**
     * Helper method to iterate over source vertices in graml and add them to the graph.
     *
     * @param g
     * @param srcVertexName
     * @param edges
     */
    private void injectSourceVertices(final Graph g, final String srcVertexName, final Map<String, Object> edges) {
        Vertex srcVertex = findOrCreateVertex(g, srcVertexName);
        edges.forEach((edgeName, target) -> injectEdges(g, srcVertexName, srcVertex, edgeName, target));
    }

    /**
     * Helper method to iterate over the outgoing edges in graml and them to the graph. As the destination vertex must
     * be referenced for this operation, a lookup will be performed to find an existing target vertex matching that in
     * graml or a new vertex will be created if it does not exist yet.
     *
     * @param g
     * @param srcVertexName
     * @param srcVertex
     * @param edgeName
     * @param target
     */
    private void injectEdges(final Graph g, final String srcVertexName, final Vertex srcVertex, final String edgeName,
            final Object target) {
        if (target instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> targetNames = (List<String>) target;
            targetNames.forEach(targetName -> injectEdges(g, srcVertexName, srcVertex, edgeName, targetName));
        } else if (target instanceof Map) {
            throw new GramlException(format("Source vertex \"%s\" may not use an arbitrary map as an edge target.",
                    srcVertexName));
        } else {
            Vertex targetVertex = findOrCreateVertex(g, (String) target);
            // TODO: Enable custom edge classes

            Edge edge = srcVertex.addEdge(edgeName, targetVertex);
            edgeProps.updateEdgeProperties(edgeName, edge);
        }
    }

    /**
     * Helper method to find an existing vertex. First the local cache is checked, then the in-memory (uncommitted)
     * graph object, the finally the database.
     *
     * @param g
     * @param vertexName
     * @return
     */
    private Vertex findOrCreateVertex(final Graph g, final String vertexName) {
        String resolvedVertexName = classmap.resolveVertex(vertexName);
        boolean usingClasses = !vertexName.equals(resolvedVertexName);

        Vertex vertex = (Vertex) vertexCache.get(vertexName);
        if (vertex == null) {
            // FIXME Properly query for vertices in DB if getByID misses
            if (!usingClasses) {
                vertex = g.getVertex(resolvedVertexName);
            }

            if (vertex == null) {
                vertex = g.addVertex(resolvedVertexName);
                if (usingClasses) {
                    vertex.setProperty(GraphSectionImpl.ELEMENT_NAME_PROPERTY, vertexName);
                }
            }
        }

        vertexProps.updateVertexProperties(vertexName, vertex);
        vertexCache.put(vertexName, vertex);
        return vertex;
    }
}