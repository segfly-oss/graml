package org.segfly.graml.model.impl

import org.segfly.graml.model.impl.EdgesSectionImpl;

import spock.lang.*

import com.tinkerpop.blueprints.Edge
import com.tinkerpop.blueprints.impls.tg.TinkerGraph


class EdgesSectionImplSpec extends Specification {

    Edge edge

    def setup() {
        def g = new TinkerGraph()
        def v = g.addVertex("source")
        def v2 = g.addVertex("dest")

        // Test with a class name prefix to ensure proper lookup from yaml
        edge = v.addEdge("parent:test", v2)
    }


    def updateEdgeProperties() {
        setup:
        def section = [test: [foo: 'string', bar: [1, 2, 3], baz:[key: 'value']]]
        def edgesSection = new EdgesSectionImpl(section)

        when:
        edgesSection.updateEdgeProperties('test', edge)

        then:
        edge.getProperty('foo') == 'string'
        edge.getProperty('bar') == [1, 2, 3]
        edge.getProperty('baz') == [key: 'value']
    }

    def propertyLookupMiss() {
        setup:
        def section = [:]
        def edgesSection = new EdgesSectionImpl(section)

        when:
        edgesSection.updateEdgeProperties('test', edge)

        then:
        true // No NPE encountered
    }
}
