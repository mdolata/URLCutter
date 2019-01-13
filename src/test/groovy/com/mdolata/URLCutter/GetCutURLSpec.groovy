package com.mdolata.URLCutter

import spock.lang.Specification


class GetCutURLSpec extends Specification {
    def publicApi

    void setup() {
        def properties = new Properties("mdolata.com", 5,3)
        def cutService = new CutService(properties)

        publicApi = new PublicApi(cutService)
    }

    def "should return false when cut url does not exists"() {
        given:
        def url = "http://test1"

        when:
        publicApi.getCutURL(url)

        then:
        NoSuchElementException ex = thrown()

        ex.message == "Collection contains no element matching the predicate."
    }

    def "should cut url true when exists"() {
        given:
        def url = "http://test1"
        def cutURL = publicApi.cutURL(url)

        when:
        def receivedCutURL = publicApi.getCutURL(url)

        then:
        cutURL == receivedCutURL
    }
}