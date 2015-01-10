package org.segfly.graml.model

import spock.lang.*

import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.tg.TinkerGraph


class VerticesSectionImplSpec extends Specification {

    // Test with a class name prefix to ensure proper lookup from yaml
    Vertex v = new TinkerGraph().addVertex("parent:test")

    def updateVertexProperties() {
        setup:
        def section = [test: [foo: 'string', bar: [1, 2, 3], baz:[key: 'value']]]
        def verticiesSection = new VerticesSectionImpl(section)

        when:
        verticiesSection.updateVertexProperties('test', v)

        then:
        v.getProperty('foo') == 'string'
        v.getProperty('bar') == [1, 2, 3]
        v.getProperty('baz') == [key: 'value']
    }

    def propertyLookupMiss() {
        setup:
        def section = [:]
        def verticiesSection = new VerticesSectionImpl(section)

        when:
        verticiesSection.updateVertexProperties('test', v)

        then:
        true // No NPE encountered
    }
}
