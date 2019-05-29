package com.mdolata.URLCutter

import com.mdolata.URLCutter.dao.PairDAO
import com.mdolata.URLCutter.dao.Properties
import com.mdolata.URLCutter.services.CrudService
import com.mdolata.URLCutter.services.CutService
import com.mdolata.URLCutter.services.RandomStringService
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll


class CutURLSpec extends Specification {

    def publicApi
    def properties

    void setup() {
        properties = new Properties("mdolata.com", 5, 3)
        def db = new PairDAO()
        def crudService = new CrudService(db)
        def randomStringService = new RandomStringService(crudService, properties)
        def cutService = new CutService(crudService, randomStringService, db, properties)

        publicApi = new PublicApi(crudService, cutService)

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
        def responses = []
        expect:
        for (int i = 0; i < 50000; i++) {
            def cutURL = publicApi.cutURL(urlTemplate + i)
            assert responses.add(cutURL)
        }
    }

    @Ignore
    def "should throw exception when create is failed"() {
        given:
        def urlTemplate = "http://test"

        when:
        for (int i = 0; i < 238328 + 1; i++) {
            publicApi.cutURL(urlTemplate + i)
        }

        then:
        RuntimeException ex = thrown()

        ex.message == "Creating cutURL failed"
    }
}
