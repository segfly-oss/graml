package org.segfly.graml

import spock.lang.*

import com.tinkerpop.blueprints.impls.tg.TinkerGraph
import com.tinkerpop.blueprints.util.io.graphson.GraphSONWriter

class GramlReaderSpec extends Specification {
    def g = new TinkerGraph()

    def readBasicGraph() {
        setup:
        def graml = new GramlReader(g)

        when:
        def graphson = loadGramlGraph(g, graml, basicYaml)

        then:
        graphson.contains("""{"_id":"machine:car","_type":"vertex"}""")
        graphson.contains("""{"_id":"structure:road","_type":"vertex"}""")
        graphson.contains("""{"_id":"V:truck","_type":"vertex"}""")
        graphson.contains("""{"_id":"V:dirt","_type":"vertex"}""")
        graphson.contains("""{"_id":"0","_type":"edge","_outV":"machine:car","_inV":"structure:road","_label":"E:driveOn"}""")
        graphson.contains("""{"_id":"1","_type":"edge","_outV":"machine:car","_inV":"V:sydney","_label":"geospatial:location"}""")
        graphson.contains("""{"_id":"2","_type":"edge","_outV":"V:truck","_inV":"structure:road","_label":"E:driveOn"}""")
        graphson.contains("""{"_id":"3","_type":"edge","_outV":"V:truck","_inV":"V:dirt","_label":"E:driveOn"}""")
    }

    private def loadGramlGraph(g, graml, yaml) {
        graml.load(yaml)
        def baos= new ByteArrayOutputStream()
        GraphSONWriter.outputGraph(g, baos)
        return baos.toString()
    }

    def basicYaml = """
            classmap:
              defaults:
                 vertex: V
                 edge: E
              machine: [car]
              structure: [road]
              geospatial: location
            graph:
              car: {driveOn: road, location: sydney}
              truck: {driveOn: [road, dirt]}
    """
}