package com.mdolata.URLCutter

import spock.lang.Specification


class GetAllCutURLsSpec extends Specification {

    def publicApi

    void setup() {
        def properties = new Properties("mdolata.com", 5, 3)
        def cutService = new CutService(properties)

        publicApi = new PublicApi(cutService)
    }

    def "should return empty list when nothing has been added"() {
        when:
        def allURLs = publicApi.getAllCutURLs()

        then:
        allURLs.size == 0
    }

    def "should return one element list when one url has been added"() {
        given:
        publicApi.cutURL("test1")

        when:
        def allURLs = publicApi.getAllCutURLs()

        then:
        allURLs.size == 1
    }

    def "should return one element list when url has been added twice"() {
        given:
        publicApi.cutURL("test1")
        publicApi.cutURL("test1")

        when:
        def allURLs = publicApi.getAllCutURLs()

        then:
        allURLs.size == 1
    }

    def "should return n element list when n urls have been added "() {
        given:
        def n = 5
        for (int i = 0; i < n; i++) {
            publicApi.cutURL("test$i")
        }

        when:
        def allURLs = publicApi.getAllCutURLs()

        then:
        allURLs.size == 5
    }
}