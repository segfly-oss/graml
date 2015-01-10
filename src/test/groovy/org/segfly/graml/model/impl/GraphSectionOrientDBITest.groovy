package org.segfly.graml.model.impl

import org.segfly.graml.model.ClassmapSection
import org.segfly.graml.model.EdgesSection
import org.segfly.graml.model.VerticesSection

import spock.lang.*

import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx

class GraphSectionOrientDBITest extends Specification {

    OrientGraphNoTx g = new OrientGraphNoTx('memory:GraphSectionOrientDBITest')

    VerticesSection stubVerticesProps = Stub()
    EdgesSection stubEdgesProps = Stub()

    def setup() {
        // Create a unique in-memory database to avoid collisions with parallel test runs
        def dbId = new Random().nextInt(Integer.MAX_VALUE)
        g = new OrientGraphNoTx("memory:${dbId}")
    }

    def cleanup() {
        g.drop()
    }

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
