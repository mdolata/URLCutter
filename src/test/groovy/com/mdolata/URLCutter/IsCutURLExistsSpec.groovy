package com.mdolata.URLCutter

import spock.lang.Specification


class IsCutURLExistsSpec extends Specification {
    def publicApi

    void setup() {
        def properties = new Properties("mdolata.com", 5,3)
        def cutService = new CutService(properties)

        publicApi = new PublicApi(cutService)
    }

    def "should return false when cut url does not exists"() {
        given:
        def cutURL = "testCut"

        when:
        def isCutURLExists = publicApi.isCutURLExists(cutURL)

        then:
        !isCutURLExists
    }

    def "should return true when cut url exists"() {
        given:
        def url = "http://test1"
        def cutURL = publicApi.cutURL(url)

        when:
        def isCutURLExists = publicApi.isCutURLExists(cutURL)

        then:
        isCutURLExists
    }
}
