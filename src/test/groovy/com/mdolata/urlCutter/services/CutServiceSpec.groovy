package com.mdolata.urlCutter.services

import com.mdolata.urlCutter.dao.PairDAO
import com.mdolata.urlCutter.dao.Properties
import com.mdolata.urlCutter.utils.RandomStringGenerator
import spock.lang.Specification
import spock.lang.Subject

class CutServiceSpec extends Specification {

    @Subject
    def cutService
    def properties

    void setup() {
        properties = new Properties("dummy.address.com", 5, 3)
        def pairDao = new PairDAO()
        def crudService = new CrudService(pairDao)
        def randomStringService = new RandomStringService(crudService, new RandomStringGenerator(), properties)

        cutService = new CutService(crudService, randomStringService, properties)
    }

    def "should return the same cut url for for two same calls"(){
        setup:
        def dummyUrl = "www.dummy.com"

        when:
        def firstResult = cutService.createCutURL(dummyUrl)
        def secondResult = cutService.createCutURL(dummyUrl)

        then:
        firstResult == secondResult
    }

    def "should return the different cut url for for two different calls"(){
        setup:
        def firstDummyUrl = "www.dummy.com"
        def secondDummyUrl = "www.secondDummy.com"

        when:
        def firstResult = cutService.createCutURL(firstDummyUrl)
        def secondResult = cutService.createCutURL(secondDummyUrl)

        then:
        firstResult != secondResult
    }

    def "should always return created cut url"(){
        setup:
        def firstDummyUrl = "www.dummy.com"
        def secondDummyUrl = "www.secondDummy.com"

        when:
        def firstResult = cutService.createCutURL(firstDummyUrl)
        def secondResult = cutService.createCutURL(secondDummyUrl)

        then:
        firstResult != secondResult

        when:
        def thirdResult = cutService.createCutURL(firstDummyUrl)
        def fourthResult = cutService.createCutURL(secondDummyUrl)

        then:
        firstResult == thirdResult
        secondResult == fourthResult
    }

    def "should return new custom cut url when both url and cut url don't exists"() {
        setup:
        def dummyUrl = "www.dummy.com"
        def dummyCutUrl = "custom/cut/url"
        def expectedResult = properties.base + "/" + dummyCutUrl

        when:
        def result = cutService.createCustomCutURL(dummyUrl, dummyCutUrl)

        then:
        result == expectedResult
    }

    def "should return new custom cut url when url exists but cut url not "() {
        setup:
        def dummyUrl = "www.dummy.com"
        def firstDummyCutUrl = "first/custom/cut/url"
        def secondDummyCutUrl = "second/custom/cut/url"
        def firstExpectedResult = properties.base + "/" + firstDummyCutUrl
        def secondExpectedResult = properties.base + "/" + secondDummyCutUrl

        when:
        def firstResult = cutService.createCustomCutURL(dummyUrl, firstDummyCutUrl)

        then:
        firstResult == firstExpectedResult

        when:
        def secondResult = cutService.createCustomCutURL(dummyUrl, secondDummyCutUrl)

        then:
        secondResult == secondExpectedResult
    }

    def "should return already created custom cut url when a pair of url and cut url exists"() {
        setup:
        def dummyUrl = "www.dummy.com"
        def dummyCutUrl = "custom/cut/url"
        def expectedResult = properties.base + "/" + dummyCutUrl

        when:
        def result = cutService.createCustomCutURL(dummyUrl, dummyCutUrl)

        then:
        result == expectedResult

        when:
        def result2 = cutService.createCustomCutURL(dummyUrl, dummyCutUrl)

        then:
        result2 == expectedResult
    }

    def "should thrown exception when custom cut url is already created for another url"() {
        setup:
        def firstDummyUrl = "www.dummy.com"
        def secondDummyUrl = "www.second.dummy.com"
        def dummyCutUrl = "custom/cut/url"
        def expectedResult = properties.base + "/" + dummyCutUrl

        when:
        def result = cutService.createCustomCutURL(firstDummyUrl, dummyCutUrl)

        then:
        result == expectedResult

        when:
        cutService.createCustomCutURL(secondDummyUrl, dummyCutUrl)

        then:
        RuntimeException ex = thrown()
        ex.message == "cut url already exists"
    }
}
