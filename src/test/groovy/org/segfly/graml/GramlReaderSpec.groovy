package org.segfly.graml

import spock.lang.*

import com.tinkerpop.blueprints.impls.tg.TinkerGraph
import com.tinkerpop.blueprints.util.io.graphson.GraphSONWriter

class GramlReaderSpec extends Specification {
    def g = new TinkerGraph()

    def readMinimalGraph() {
        setup:
        def graml = new GramlReader(g)
        def yaml = """graph: {source: {edge: target}}"""

        when:
        def graphson = loadGramlGraph(g, graml, yaml)

        then:
        graphson.contains("""{"_id":"source","_type":"vertex"}""")
        graphson.contains("""{"_id":"target","_type":"vertex"}""")
        graphson.contains("""{"_id":"0","_type":"edge","_outV":"source","_inV":"target","_label":"edge"}""")
    }

    def readComplexGraph() {
        setup:
        def graml = new GramlReader(g)

        when:
        def graphson = loadGramlGraph(g, graml, complexYaml)

        then:
        graphson.contains("""{"_id":"V:home","_type":"vertex"}""")
        graphson.contains("""{"_id":"machine:car","_type":"vertex"}""")
        graphson.contains("""{"tires":"goodyear","_id":"V:truck","_type":"vertex"}""")
        graphson.contains("""{"_id":"V:dirt","_type":"vertex"}""")
        graphson.contains("""{"lanes":["north","south"],"_id":"structure:road","_type":"vertex"}""")
        graphson.contains("""{"lon":150.9224326,"lat":-33.7969235,"tires":"goodyear","_id":"V:sydney","_type":"vertex"}""")
        graphson.contains("""{"_id":"0","_type":"edge","_outV":"machine:car","_inV":"structure:road","_label":"E:driveOn"}""")
        graphson.contains("""{"coordStyle":"cartesian","_id":"1","_type":"edge","_outV":"machine:car","_inV":"V:sydney","_label":"geospatial:location"}""")
        graphson.contains("""{"_id":"2","_type":"edge","_outV":"machine:car","_inV":"V:home","_label":"structure:garaged"}""")
        graphson.contains("""{"_id":"3","_type":"edge","_outV":"V:truck","_inV":"structure:road","_label":"E:driveOn"}""")
        graphson.contains("""{"_id":"4","_type":"edge","_outV":"V:truck","_inV":"V:dirt","_label":"E:driveOn"}""")
    }

    private def loadGramlGraph(g, graml, yaml) {
        graml.load(yaml)
        def baos= new ByteArrayOutputStream()
        GraphSONWriter.outputGraph(g, baos)
        return baos.toString()
    }

    def complexYaml = """
            classmap:
              defaults:
                 vertex: V
                 edge: E
              machine: [car]
              structure: [road, garaged]
              geospatial: location
            graph:
              car: {driveOn: road, location: sydney, garaged: home}
              truck: {driveOn: [road, dirt]}
            vertices:
              truck: {tires: goodyear}
              sydney: {tires: goodyear, lat: -33.7969235, lon: 150.9224326}
              road: {lanes: [north, south]}
            edges:
              location: {coordStyle: cartesian}
    """
}