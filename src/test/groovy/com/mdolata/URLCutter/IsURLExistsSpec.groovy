package com.mdolata.URLCutter

import spock.lang.Specification


class IsURLExistsSpec extends Specification {

    def publicApi
    def properties

    void setup() {
        properties = new Properties("mdolata.com", 5,3)
        def cutService = new CutService(properties)

        publicApi = new PublicApi(cutService)

    }

    def "should return false when url does not exists"() {
        given:
        def url = "http://test"

        when:
        def isURLExists = publicApi.isURLExists(url)

        then:
        !isURLExists
    }

    def "should return true when url does exists"() {
        given:
        def url = "http://test1"
        publicApi.cutURL(url)

        when:
        def isURLExists = publicApi.isURLExists(url)

        then:
        isURLExists
    }
}
