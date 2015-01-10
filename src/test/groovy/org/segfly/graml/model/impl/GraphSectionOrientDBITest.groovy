package org.segfly.graml.model.impl

import org.segfly.graml.model.ClassmapSection
import org.segfly.graml.model.EdgesSection
import org.segfly.graml.model.VerticesSection
import org.segfly.graml.model.impl.GraphSectionImpl

import spock.lang.*

import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx

class GraphSectionOrientDBITest extends Specification {

    OrientGraphNoTx g = new OrientGraphNoTx('memory:GraphSectionOrientDBITest')

    VerticesSection stubVerticesProps = Stub()
    EdgesSection stubEdgesProps = Stub()

    def orientDbClasses() {
        given:
        ClassmapSection stubClassmap = Stub() {
            resolveEdge(_) >> { "class:MyEdge" }
            resolveVertex(_) >> { "class:MyVertex" }
        }

        def section = [source: [edge: 'target1', edge2: 'target2']]
        def graphSection = new GraphSectionImpl(section, stubClassmap, stubVerticesProps, stubEdgesProps)

        when:
        graphSection.inject(g)

        then:
        g.getVerticesOfClass("MyVertex").count({it}) == 3
    }
}
