package org.segfly.graml

import org.yaml.snakeyaml.Yaml

import spock.lang.*

import com.tinkerpop.blueprints.impls.orient.OrientBaseGraph
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
        File yamlfile = new File(getClass().getResource('/yaml/orientTestGraph.yaml').path)

        when:
        def graphson = loadGramlGraph(g, graml, yamlfile)

        then:
        verifyGraph(g, graphson)
    }

    def readFromURL() {
        given:
        def graml = new GramlReader(g)
        URL yamlfile = this.getClass().getResource('/yaml/orientTestGraph.yaml')

        when:
        def graphson = loadGramlGraph(g, graml, yamlfile)

        then:
        verifyGraph(g, graphson)
    }

    private def verifyGraph(OrientBaseGraph g, String graphson) {
        assert g.getVerticesOfClass("machine").count({ true }) == 1
        assert g.getVerticesOfClass("structure").count({ true }) == 2
        assert g.getVerticesOfClass("geospatial").count({ true }) == 1

        assert graphson.find(/\Q{"name":"truck","tires":"goodyear","_id":"\E.*\Q","_type":"vertex"}\E/)
        assert graphson.find(/\Q{"name":"dirt","_id":"\E.*\Q","_type":"vertex"}\E/)
        assert graphson.find(/\Q{"name":"car","_id":"\E.*\Q","_type":"vertex"}\E/)
        assert graphson.find(/\Q{"name":"road","lanes":["north","south"],"_id":"\E.*\Q","_type":"vertex"}\E/)
        assert graphson.find(/\Q{"name":"home","_id":"\E.*\Q","_type":"vertex"}\E/)
        assert graphson.find(/\Q{"name":"sydney","lon":150.9224326,"lat":-33.7969235,"_id":"\E.*\Q","_type":"vertex"}\E/)

        // They only heavyweight edge due to the addition of properties on this edge
        assert graphson.find(/\Q{"coordStyle":"cartesian","_id":"\E.*\Q","_type":"edge","_outV":"\E.*\Q","_inV":"\E.*\Q","_label":"location"}\E/)
        true
    }


    private def loadGramlGraph(g, graml, yaml) {
        graml.load(yaml)
        def baos= new ByteArrayOutputStream()
        GraphSONWriter.outputGraph(g, baos)
        return baos.toString()
    }
}