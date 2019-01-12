package com.mdolata.URLCutter

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll


class CutURLSpec extends Specification {

    def publicApi
    def properties

    void setup() {
        properties = new Properties("mdolata.com", 6,3)
        def cutService = new CutService(properties)

        publicApi = new PublicApi(cutService)

    }

    @Unroll
    def "successfully should return short url"() {
        when:
        def cutURL = publicApi.cutURL(url)

        then:
        cutURL.contains(properties.base)
        cutURL.replace(properties.base + "/", "").length() == properties.URLLength


        where:
        no | url
        1  | "http://test"
        2  | "https://test1234"
        3  | "https://test/test1/test2"
    }

    def "should return the same cut url for the same requested urls"() {
        given:
        def url = "http://test"
        def cutUrl = publicApi.cutURL(url)

        when:
        def cutUrl2 = publicApi.cutURL(url)

        then:
        cutUrl == cutUrl2
    }

    def "should return the different cut url for the different requested urls"() {
        given:
        def url1 = "http://test"
        def url2 = "http://test2"
        def cutUrl1 = publicApi.cutURL(url1)
        def cutUrl2 = publicApi.cutURL(url2)

        expect:
        cutUrl1 != cutUrl2
    }

    @Ignore
    def "should return the different cut url for every unique request"() {
        given:
        def urlTemplate = "http://test"

        expect:
        for (int i = 0; i < 10000; i++) {
            String url = urlTemplate + i
            def cutURL = publicApi.cutURL(url)
            assert (publicApi.isCutURLExists(cutURL) == false)
        }
    }
}
