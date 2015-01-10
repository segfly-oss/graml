package org.segfly.graml;

import java.util.Map;

import org.segfly.graml.model.ClassmapSection;
import org.segfly.graml.model.EdgesSection;
import org.segfly.graml.model.GramlFactory;
import org.segfly.graml.model.GraphSection;
import org.segfly.graml.model.VerticesSection;
import org.segfly.graml.model.impl.GramlFactoryImpl;
import org.yaml.snakeyaml.Yaml;

import com.tinkerpop.blueprints.Graph;

/**
 * @author Nicholas Pace
 * @since Jan 3, 2015
 */
public class GramlReader {
    private Graph        target;
    private Yaml         ymlProc;

    private GramlFactory graml;

    public GramlReader(final Graph target) {
        this(target, new Yaml(), new GramlFactoryImpl());
    }

    GramlReader(final Graph target, final Yaml ymlProc, final GramlFactory graml) {
        super();
        this.target = target;
        this.ymlProc = ymlProc;
        this.graml = graml;
    }

    public void load(final String yaml) throws GramlException {
        // Read the YAML
        @SuppressWarnings("rawtypes")
        Map fullMap = (Map) ymlProc.load(yaml);

        // Decompose the sections
        ClassmapSection classmap = graml.getClassmapSection(fullMap);
        EdgesSection edgeProps = graml.getEdgesSection(fullMap);
        VerticesSection vertexProps = graml.getVerticesSection(fullMap);
        GraphSection graph = graml.getGraphSection(fullMap, classmap, vertexProps, edgeProps);

        // Alter the graph target
        graph.inject(target);
    }
}
