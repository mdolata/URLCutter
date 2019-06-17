package com.mdolata.urlCutter

import arrow.core.Either
import com.mdolata.urlCutter.dao.CreationError
import com.mdolata.urlCutter.dao.PairDAO
import com.mdolata.urlCutter.dao.Properties
import com.mdolata.urlCutter.services.CrudService
import com.mdolata.urlCutter.services.CutService
import com.mdolata.urlCutter.services.RandomStringService
import com.mdolata.urlCutter.utils.RandomStringGenerator
import spock.lang.Specification

class CreateCustomCutURLSpec extends Specification {

    def publicApi
    def properties

    void setup() {
        properties = new Properties("dummy.com", 5, 3)
        def db = new PairDAO()
        def crudService = new CrudService(db)
        def stringGenerator = new RandomStringGenerator()
        def randomStringService = new RandomStringService(crudService, stringGenerator, properties)
        def cutService = new CutService(crudService, randomStringService, properties)

        publicApi = new PublicApi(crudService, cutService)
    }

    def "should create custom cut url when cut url does not already exists"() {
        given:
        def customCutURL = "simpleTest"

        when:
        Either<CreationError, String> createdCustomCutURL = publicApi.createCustomCutURL("test", customCutURL)

        then:
        createdCustomCutURL.toString() == right(properties.base + "/" + customCutURL as String)
    }

    def "should create new custom cut url when url exists"() {
        given:
        def customCutURL = "simpleTest"
        def url = "test"
        def createdCutURL = publicApi.createCutURL(url)

        when:
        def createdCustomCutURL = publicApi.createCustomCutURL(url, customCutURL)

        then:
        createdCustomCutURL.toString() == right(properties.base + "/" + customCutURL as String)

        and:
        createdCustomCutURL != createdCutURL
    }

    def "should return the same custom cut urls for two the same requests and should not be duplicates"() {
        given:
        def customCutURL = "simpleTest"
        def url = "test"
        def expectedCutURL = properties.base + "/" + customCutURL

        when:
        def createdCustomCutURL1 = publicApi.createCustomCutURL(url, customCutURL)
        def createdCustomCutURL2 = publicApi.createCustomCutURL(url, customCutURL)

        then:
        createdCustomCutURL1 == createdCustomCutURL2
        right(expectedCutURL as String) == createdCustomCutURL1.toString()

        and:
        publicApi.getAllCutURLs().stream().filter { cutURL -> cutURL == expectedCutURL }.count() == 1
    }

    def "should throw exception and not create custom cut url when custom cut url already exists for different url"() {
        given:
        def customCutURL = "simpleTest"
        def urlOk = "test"
        def urlFailed = urlOk + "2"
        def expectedCutURL = properties.base + "/" + customCutURL

        when:
        def createdCustomCutURL1 = publicApi.createCustomCutURL(urlOk, customCutURL)
        Either<CreationError, String> result = publicApi.createCustomCutURL(urlFailed, customCutURL)

        then:
        createdCustomCutURL1.toString() == right(expectedCutURL as String)

        and:
        result.toString() == left("CreationError(message=Pair of ($urlFailed, $expectedCutURL) already exists)")

        and:
        publicApi.getAllCutURLs().stream().filter { cutURL -> cutURL == expectedCutURL }.count() == 1
    }

    def "should throw exception and not create custom cut url when simple cut url exists"() {
        given:
        def url = "testURL"
        String simpleCutURL = publicApi.createCutURL(url)
        def path = simpleCutURL.split("/")[1]

        when:
        def result = publicApi.createCustomCutURL("test", path)

        and:
        result.toString() == left("CreationError(message=Pair of ($url, $simpleCutURL) already exists)")

        then:
        publicApi.getAllCutURLs().stream().filter { cutURL -> cutURL == simpleCutURL }.count() == 1
    }

    String right(String s) {
        return "Right(b=$s)"
    }
    String left(String s) {
        return "Left(a=$s)"
    }
}
