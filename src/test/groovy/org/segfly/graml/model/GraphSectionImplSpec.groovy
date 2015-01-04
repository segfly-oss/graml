package org.segfly.graml.model

import org.segfly.graml.GramlException

import spock.lang.*

import com.tinkerpop.blueprints.impls.tg.TinkerGraph
import com.tinkerpop.blueprints.util.io.graphson.GraphSONWriter

class GraphSectionImplSpec extends Specification {

    TinkerGraph g = new TinkerGraph()

    ClassmapSection stubClassmap = Stub() {
        resolveEdge(_) >> { "e:${it[0]}" }
        resolveVertex(_) >> { "v:${it[0]}" }
    }

    def createsGraphTuples() {
        setup:
        def section = [source: [edge: 'target1', edge2: 'target2']]
        def graphSection = new GraphSectionImpl(section, stubClassmap)

        when:
        def graphson = injectGraph(graphSection, g)

        then:
        graphson.contains("""{"_id":"v:source","_type":"vertex"}""")
        graphson.contains("""{"_id":"v:target1","_type":"vertex"}""")
        graphson.contains("""{"_id":"v:target2","_type":"vertex"}""")
        graphson.contains("""{"_id":"0","_type":"edge","_outV":"v:source","_inV":"v:target1","_label":"e:edge"}""")
        graphson.contains("""{"_id":"1","_type":"edge","_outV":"v:source","_inV":"v:target2","_label":"e:edge2"}""")
    }

    def createPolytargets() {
        setup:
        def section = [source: [edge: ['target1', 'target2']]]
        def graphSection = new GraphSectionImpl(section, stubClassmap)

        when:
        def graphson = injectGraph(graphSection, g)

        then:
        graphson.contains("""{"_id":"v:source","_type":"vertex"}""")
        graphson.contains("""{"_id":"v:target1","_type":"vertex"}""")
        graphson.contains("""{"_id":"v:target2","_type":"vertex"}""")
        graphson.contains("""{"_id":"0","_type":"edge","_outV":"v:source","_inV":"v:target1","_label":"e:edge"}""")
        graphson.contains("""{"_id":"1","_type":"edge","_outV":"v:source","_inV":"v:target2","_label":"e:edge"}""")
    }

    def missingGraphSectionThrowsException() {
        when:
        new GraphSectionImpl(null, stubClassmap)

        then:
        thrown(GramlException)
    }

    private def injectGraph(graphSection, g) {
        graphSection.inject(g)
        def baos= new ByteArrayOutputStream()
        GraphSONWriter.outputGraph(g, baos)
        return baos.toString()
    }
}
