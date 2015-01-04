package org.segfly.graml.model;

import java.util.List;
import java.util.Map;

import org.segfly.graml.GramlException;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nicholas Pace
 * @since Jan 4, 2015
 */
public class GraphSectionImpl implements GraphSection {

    private ClassmapSection             classmap;
    private Map<String, Map<String, Object>> section;

    // TODO figure out why Map<String, ?> results in internal groovy compiler error
    public GraphSectionImpl(final Map<String, Map<String, Object>> section, final ClassmapSection classmap)
            throws GramlException {
        if (section == null) {
            throw new GramlException("Missing required graph section.");
        }

        this.classmap = classmap;
        this.section = section;
    }

    @Override
    public void inject(final Graph target) {
        section.forEach((srcVertex, edges) -> injectSourceNodes(target, srcVertex, edges));
    }

    private void injectSourceNodes(final Graph g, final String srcVertexName, final Map<String, Object> edges) {
        Vertex srcVertex = findOrCreateVertex(g, srcVertexName);
        edges.forEach((edgeName, target) -> injectEdges(g, srcVertex, edgeName, target));
    }

    private void injectEdges(final Graph g, final Vertex srcVertex, final String edgeName, final Object target) {
        if (target instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> targetNames = (List<String>) target;
            targetNames.forEach(targetName -> injectEdges(g, srcVertex, edgeName, targetName));
        } else {
            Vertex targetVertex = findOrCreateVertex(g, (String) target);
            srcVertex.addEdge(classmap.resolveEdge(edgeName), targetVertex);
        }
    }

    private Vertex findOrCreateVertex(final Graph g, final String vertexName) {
        Vertex vertex = g.addVertex(classmap.resolveVertex(vertexName));
        return vertex;
    }

}
