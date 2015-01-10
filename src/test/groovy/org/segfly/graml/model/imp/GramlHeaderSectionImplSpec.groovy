package org.segfly.graml.model.imp

import org.segfly.graml.GramlException
import org.segfly.graml.model.impl.GramlHeaderSectionImpl

import spock.lang.*

import static org.segfly.graml.model.GramlHeaderSection.GRAML_HEADER_SECTION
import static org.segfly.graml.model.GramlHeaderSection.GRAML_VERSION
import static org.segfly.graml.model.GramlHeaderSection.GRAML_VERSION_FIELD


class GramlHeaderSectionImplSpec extends Specification {

    def basicChecksPass() {
        when:
        def map = new GramlHeaderSectionImpl([version:1.0f])

        then:
        true //Everything passed!
    }

    def missingVersion() {
        when:
        def map = new GramlHeaderSectionImpl([foo:"bar"])

        then:
        thrown(GramlException)
    }

    def notANumber() {
        when:
        def map = new GramlHeaderSectionImpl([version:"awesome-version"])

        then:
        thrown(GramlException)
    }

    def wrongVersion() {
        when:
        def map = new GramlHeaderSectionImpl([version:1024])

        then:
        thrown(GramlException)
    }

    def missingHeader() {
        when:
        def map = new GramlHeaderSectionImpl(null)

        then:
        thrown(GramlException)
    }
}
