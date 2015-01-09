package org.segfly.graml.model

import spock.lang.*

class ClassmapSectionImplSpec extends Specification {

    def classmapSectionIsPivoted() {
        setup:
        def section = [c1:['v0', 'v1', 'v2'], c2:['v3', 'v4']]
        def map = new ClassmapSectionImpl(section)

        expect:
        map[vertex] == classname

        where:
        vertex || classname
        'v0'   || 'c1'
        'v1'   || 'c1'
        'v2'   || 'c1'
        'v3'   || 'c2'
        'v4'   || 'c2'
    }

    def fullyResolvedEntities() {
        setup:
        def section = [c1:['v0', 'v1', 'e2'], c2:['v3', 'e4']]
        def map = new ClassmapSectionImpl(section)

        expect:
        map.resolveEdge(entity) == fqn
        map.resolveVertex(entity) == fqn

        where:
        entity || fqn
        'v0'   || 'c1:v0'
        'v1'   || 'c1:v1'
        'e2'   || 'c1:e2'
        'v3'   || 'c2:v3'
        'e4'   || 'c2:e4'
    }

    def allowsSingleMappings() {
        setup:
        def section = [c1: 'v0']
        def map = new ClassmapSectionImpl(section)

        when:
        def classname = map['v0']

        then:
        classname == 'c1'
    }

    def missingClassmapSectionIsAllowed() {
        setup:
        def map = new ClassmapSectionImpl(null)

        when:
        def classname = map['v0']
        def resolvedVertex = map.resolveVertex('v0')
        def resolvedEdge = map.resolveEdge('e0')

        then:
        classname == null
        resolvedVertex == 'v0'
        resolvedEdge == 'e0'
    }
}
