package org.segfly.graml

import org.segfly.graml.model.impl.GramlFactoryImpl
import org.yaml.snakeyaml.Yaml

import spock.lang.*

import com.tinkerpop.blueprints.impls.tg.TinkerGraph
import com.tinkerpop.blueprints.util.io.graphson.GraphSONWriter

class GramlReaderSpec extends Specification {

    def g = new TinkerGraph()
    def yamlProc = new Yaml()

    // Use this partial mock to skip the header check for more concise tests
    GramlFactoryImpl gramlFactorySpy = Spy() { getGramlHeaderSection(_) >> null }

    def requiredHeader() {
        setup:
        def graml = new GramlReader(g)

        when:
        def graphson = loadGramlGraph(g, graml, """graph: {source: {edge: target}}""")

        then:
        thrown(GramlException)
    }

    def readMinimalGraph() {
        setup:
        def graml = new GramlReader(g, yamlProc, gramlFactorySpy)
        def yaml = """graph: {source: {edge: target}}"""

        when:
        def graphson = loadGramlGraph(g, graml, yaml)

        then:
        graphson.contains("""{"_id":"source","_type":"vertex"}""")
        graphson.contains("""{"_id":"target","_type":"vertex"}""")
        graphson.contains("""{"_id":"0","_type":"edge","_outV":"source","_inV":"target","_label":"edge"}""")
    }

    private def loadGramlGraph(g, graml, yaml) {
        graml.load(yaml)
        def baos= new ByteArrayOutputStream()
        GraphSONWriter.outputGraph(g, baos)
        return baos.toString()
    }
}