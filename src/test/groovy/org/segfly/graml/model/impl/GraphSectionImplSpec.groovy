package org.segfly.graml.model.impl

import org.segfly.graml.GramlException
import org.segfly.graml.model.ClassmapSection
import org.segfly.graml.model.EdgesSection
import org.segfly.graml.model.VerticesSection
import org.segfly.graml.model.impl.ClassmapSectionImpl
import org.segfly.graml.model.impl.GraphSectionImpl

import spock.lang.*

import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.tg.TinkerGraph
import com.tinkerpop.blueprints.util.io.graphson.GraphSONWriter

class GraphSectionImplSpec extends Specification {

    Graph g = new TinkerGraph()
    ClassmapSection classmap = new ClassmapSectionImpl(null)

    VerticesSection stubVerticesProps = Stub()
    EdgesSection stubEdgesProps = Stub()

    def createsGraphTuples() {
        setup:
        def section = [source: [edge: 'target1', edge2: 'target2']]
        def graphSection = new GraphSectionImpl(section, classmap, stubVerticesProps, stubEdgesProps)

        when:
        def graphson = injectGraph(graphSection, g)

        then:
        graphson.contains("""{"_id":"source","_type":"vertex"}""")
        graphson.contains("""{"_id":"target1","_type":"vertex"}""")
        graphson.contains("""{"_id":"target2","_type":"vertex"}""")
        graphson.contains("""{"_id":"0","_type":"edge","_outV":"source","_inV":"target1","_label":"edge"}""")
        graphson.contains("""{"_id":"1","_type":"edge","_outV":"source","_inV":"target2","_label":"edge2"}""")
    }

    def createPolytargets() {
        setup:
        def section = [source: [edge: ['target1', 'target2']]]
        def graphSection = new GraphSectionImpl(section, classmap, stubVerticesProps, stubEdgesProps)

        when:
        def graphson = injectGraph(graphSection, g)

        then:
        graphson.contains("""{"_id":"source","_type":"vertex"}""")
        graphson.contains("""{"_id":"target1","_type":"vertex"}""")
        graphson.contains("""{"_id":"target2","_type":"vertex"}""")
        graphson.contains("""{"_id":"0","_type":"edge","_outV":"source","_inV":"target1","_label":"edge"}""")
        graphson.contains("""{"_id":"1","_type":"edge","_outV":"source","_inV":"target2","_label":"edge"}""")
    }

    def alllowDuplicateEdges() {
        setup:
        def section = [source: [edge: ['target1', 'target1']]]
        def graphSection = new GraphSectionImpl(section, classmap, stubVerticesProps, stubEdgesProps)

        when:
        def graphson = injectGraph(graphSection, g)

        then:
        // Check if there are two edges by looking for an edge with id=1 (meaning the second edge)
        graphson.contains("""{"_id":"1","_type":"edge","_outV":"source","_inV":"target1","_label":"edge"}""")
    }

    def resolveExistingVerticiesFromCache() {
        setup:
        def section = [source: [edge: 'target1', edge2: 'source2'], source2: [edge: 'target1']]
        def graphSection = new GraphSectionImpl(section, classmap, stubVerticesProps, stubEdgesProps)

        when:
        def graphson = injectGraph(graphSection, g)

        then:
        graphson.contains("""{"_id":"source","_type":"vertex"}""")
        graphson.contains("""{"_id":"source2","_type":"vertex"}""")
        graphson.contains("""{"_id":"target1","_type":"vertex"}""")
        graphson.contains("""{"_id":"0","_type":"edge","_outV":"source","_inV":"target1","_label":"edge"}""")
        graphson.contains("""{"_id":"1","_type":"edge","_outV":"source","_inV":"source2","_label":"edge2"}""")
        graphson.contains("""{"_id":"2","_type":"edge","_outV":"source2","_inV":"target1","_label":"edge"}""")
    }

    def resolveExistingVerticiesFromDB() {
        setup:
        def section1 = [source: [edge: 'target1', edge2: 'source2']]
        def section2 = [source2: [edge: 'target1']]
        def graphSection1 = new GraphSectionImpl(section1, classmap, stubVerticesProps, stubEdgesProps)
        def graphSection2 = new GraphSectionImpl(section2, classmap, stubVerticesProps, stubEdgesProps)

        when:
        graphSection1.inject(g)
        def graphson = injectGraph(graphSection2, g)

        then:
        graphson.contains("""{"_id":"source","_type":"vertex"}""")
        graphson.contains("""{"_id":"source2","_type":"vertex"}""")
        graphson.contains("""{"_id":"target1","_type":"vertex"}""")
        graphson.contains("""{"_id":"0","_type":"edge","_outV":"source","_inV":"target1","_label":"edge"}""")
        graphson.contains("""{"_id":"1","_type":"edge","_outV":"source","_inV":"source2","_label":"edge2"}""")
        graphson.contains("""{"_id":"2","_type":"edge","_outV":"source2","_inV":"target1","_label":"edge"}""")
    }

    def propertyModification() {
        setup:
        stubVerticesProps.updateVertexProperties(_,_) >> {s, Vertex v -> v.setProperty("vertexProp", "foo")}
        stubEdgesProps.updateEdgeProperties(_,_) >> {s, Edge e -> e.setProperty("edgeProp", "bar")}
        def section = [source: [edge: 'target',]]
        def graphSection = new GraphSectionImpl(section, classmap, stubVerticesProps, stubEdgesProps)

        when:
        def graphson = injectGraph(graphSection, g)

        then:
        graphson.contains("""{"vertexProp":"foo","_id":"target","_type":"vertex"}""")
        graphson.contains("""{"vertexProp":"foo","_id":"source","_type":"vertex"}""")
        graphson.contains("""{"edgeProp":"bar","_id":"0","_type":"edge","_outV":"source","_inV":"target","_label":"edge"}""")
    }

    def dissallowArbitraryNestedMaps() {
        setup:
        def section = [source: [edge: [target: 'end']]]
        def graphSection = new GraphSectionImpl(section, classmap, stubVerticesProps, stubEdgesProps)

        when:
        def graphson = injectGraph(graphSection, g)

        then:
        thrown(GramlException)
    }

    def missingGraphSectionThrowsException() {
        when:
        new GraphSectionImpl(null, classmap, stubVerticesProps, stubEdgesProps)

        then:
        thrown(GramlException)
    }

    private def injectGraph(graphSection, g) {
        graphSection.inject(g)
        def baos= new ByteArrayOutputStream()
        GraphSONWriter.outputGraph(g, baos)
        baos.close()
        return baos.toString()
    }
}
