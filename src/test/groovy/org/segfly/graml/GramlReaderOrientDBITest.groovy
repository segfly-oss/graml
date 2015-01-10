package org.segfly.graml

import org.yaml.snakeyaml.Yaml

import spock.lang.*

import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx
import com.tinkerpop.blueprints.util.io.graphson.GraphSONWriter

class GramlReaderOrientDBITest extends Specification {

    OrientGraphNoTx g
    Yaml yamlProc

    def setup() {
        yamlProc = new Yaml()
        g = new OrientGraphNoTx('memory:GramlReaderOrientDBITest')
    }

    def readFromFile() {
        given:
        def graml = new GramlReader(g)
        File yamlfile = new File(getClass().getResource('/org/segfly/graml/complexGraph.yaml').path)

        when:
        def graphson = loadGramlGraph(g, graml, yamlfile)

        then:
        verifyGraph(graphson)
    }

    def readFromURL() {
        given:
        def graml = new GramlReader(g)
        URL yamlfile = this.getClass().getResource('/org/segfly/graml/complexGraph.yaml')

        when:
        def graphson = loadGramlGraph(g, graml, yamlfile)

        then:
        verifyGraph(graphson)
    }

    private def verifyGraph(String graph) {
        assert graph.contains("""{"name":"car","_id":"#11:0","_type":"vertex"}""")
        assert graph.contains("""{"name":"road","lanes":["north","south"],"_id":"#12:0","_type":"vertex"}""")
        assert graph.contains("""{"name":"sydney","lon":150.9224326,"lat":-33.7969235,"_id":"#9:0","_type":"vertex"}""")
        assert graph.contains("""{"name":"home","_id":"#9:1","_type":"vertex"}""")
        assert graph.contains("""{"name":"truck","tires":"goodyear","_id":"#9:2","_type":"vertex"}""")
        assert graph.contains("""{"name":"dirt","_id":"#9:3","_type":"vertex"}""")

        // They only heavyweight edge due to the addition of properties on this edge
        assert graph.contains("""{"coordStyle":"cartesian","_id":"#13:0","_type":"edge","_outV":"#11:0","_inV":"#9:0","_label":"location"}""")
        true
    }

    private def loadGramlGraph(g, graml, yaml) {
        graml.load(yaml)
        def baos= new ByteArrayOutputStream()
        GraphSONWriter.outputGraph(g, baos)
        return baos.toString()
    }
}