package com.mdolata.urlCutter

import com.mdolata.urlCutter.dao.PairDAO
import com.mdolata.urlCutter.dao.Properties
import com.mdolata.urlCutter.services.CrudService
import com.mdolata.urlCutter.services.CutService
import com.mdolata.urlCutter.services.RandomStringService
import com.mdolata.urlCutter.utils.RandomStringGenerator
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll


class CutURLSpec extends Specification {

    def publicApi
    def properties

    void setup() {
        properties = new Properties("dummy.address.com", 5, 3)
        def db = new PairDAO()
        def crudService = new CrudService(db)
        def stringGenerator = new RandomStringGenerator()
        def randomStringService = new RandomStringService(crudService, stringGenerator, properties)
        def cutService = new CutService(crudService, randomStringService, properties)

        publicApi = new PublicApi(crudService, cutService)

    }

    @Unroll
    def "successfully should return short url"() {
        when:
        def cutURL = publicApi.createCutURL(url)

        then:
        cutURL.contains(properties.base)
        cutURL.replace(properties.base + "/", "").length() == properties.URLLength


        where:
        no  | url
        "1" | "http://test"
        "2" | "https://test1234"
        "3" | "https://test/test1/test2"
    }

    def "should return the same cut url for the same requested urls"() {
        given:
        def url = "http://test"
        def cutUrl = publicApi.createCutURL(url)

        when:
        def cutUrl2 = publicApi.createCutURL(url)

        then:
        cutUrl == cutUrl2
    }

    def "should return the different cut url for the different requested urls"() {
        given:
        def url1 = "http://test"
        def url2 = "http://test2"
        def cutUrl1 = publicApi.createCutURL(url1)
        def cutUrl2 = publicApi.createCutURL(url2)

        expect:
        cutUrl1 != cutUrl2
    }

    @Ignore
    def "should not return custom create cut url when url exists in system"() {}

    @Ignore
    def "should return the different cut url for every unique request"() {
        given:
        def urlTemplate = "http://test"
        def responses = []
        expect:
        for (int i = 0; i < 50000; i++) {
            def cutURL = publicApi.createCutURL(urlTemplate + i)
            assert responses.add(cutURL)
        }
    }

    @Ignore
    def "should throw exception when create is failed"() {
        given:
        def urlTemplate = "http://test"

        when:
        for (int i = 0; i < 238328 + 1; i++) {
            publicApi.createCutURL(urlTemplate + i)
        }

        then:
        RuntimeException ex = thrown()

        ex.message == "Creating createCutURL failed"
    }
}
