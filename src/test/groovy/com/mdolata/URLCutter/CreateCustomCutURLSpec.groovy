package com.mdolata.URLCutter

import com.mdolata.URLCutter.dao.PairDAO
import com.mdolata.URLCutter.dao.Properties
import com.mdolata.URLCutter.services.CrudService
import com.mdolata.URLCutter.services.CutService
import com.mdolata.URLCutter.services.RandomStringService
import spock.lang.Specification


class CreateCustomCutURLSpec extends Specification {

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

    def "should create custom cut url when cut url does not already exists"() {
        given:
        def customCutURL = "alaMaKota"

        when:
        def createdCustomCutURL = publicApi.createCustomCutURL("test", customCutURL)

        then:
        createdCustomCutURL == properties.base + "/" + customCutURL
    }

    def "should create new custom cut url when url exists"() {
        given:
        def customCutURL = "alaMaKota"
        def url = "test"
        def createdCutURL = publicApi.cutURL(url)

        when:
        def createdCustomCutURL = publicApi.createCustomCutURL(url, customCutURL)

        then:
        createdCustomCutURL == properties.base + "/" + customCutURL

        and:
        createdCustomCutURL != createdCutURL
    }

    def "should return the same custom cut urls for two the same requests and should not be duplicates"() {
        given:
        def customCutURL = "alaMaKota"
        def url = "test"
        def expectedCutURL = properties.base + "/" + customCutURL

        when:
        def createdCustomCutURL1 = publicApi.createCustomCutURL(url, customCutURL)
        def createdCustomCutURL2 = publicApi.createCustomCutURL(url, customCutURL)

        then:
        createdCustomCutURL1 == createdCustomCutURL2
        expectedCutURL == createdCustomCutURL1

        and:
        publicApi.getAllCutURLs().stream().filter { cutURL -> cutURL == expectedCutURL }.count() == 1
    }

    def "should throw exception and not create custom cut url when custom cut url already exists for different url"() {
        given:
        def customCutURL = "alaMaKota"
        def url = "test"
        def expectedCutURL = properties.base + "/" + customCutURL

        when:
        def createdCustomCutURL1 = publicApi.createCustomCutURL(url, customCutURL)
        publicApi.createCustomCutURL(url + "2", customCutURL)

        then:
        createdCustomCutURL1 == expectedCutURL

        and:
        RuntimeException ex = thrown()
        ex.message == "cut url already exists"

        and:
        publicApi.getAllCutURLs().stream().filter { cutURL -> cutURL == expectedCutURL }.count() == 1
    }

    def "should throw exception and not create custom cut url when simple cut url exists"() {
        def url = "testURL"
        given:
        String simpleCutURL = publicApi.cutURL(url)

        def path = simpleCutURL.split("/")[1]

        when:
        publicApi.createCustomCutURL("test", path)

        then:
        RuntimeException ex = thrown()
        ex.message == "cut url already exists"

        and:
        publicApi.getAllCutURLs().stream().filter { cutURL -> cutURL == simpleCutURL }.count() == 1
    }
}
