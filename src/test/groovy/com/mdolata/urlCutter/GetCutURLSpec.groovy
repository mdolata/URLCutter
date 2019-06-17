package com.mdolata.urlCutter

import com.mdolata.urlCutter.dao.PairDAO
import com.mdolata.urlCutter.dao.Properties
import com.mdolata.urlCutter.services.CrudService
import com.mdolata.urlCutter.services.CutService
import com.mdolata.urlCutter.services.RandomStringService
import com.mdolata.urlCutter.utils.RandomStringGenerator
import spock.lang.Specification


class GetCutURLSpec extends Specification {
    def publicApi

    void setup() {
        def properties = new Properties("dummy.address.com", 5, 3)
        def db = new PairDAO()
        def crudService = new CrudService(db)
        def stringGenerator = new RandomStringGenerator()
        def randomStringService = new RandomStringService(crudService, stringGenerator, properties)
        def cutService = new CutService(crudService, randomStringService, properties)

        publicApi = new PublicApi(crudService, cutService)
    }

    def "should return false when cut url does not exists"() {
        given:
        def url = "http://test1"
        def expectedDefaultCutUrl = "None"

        when:
        def result = publicApi.getCutURL(url)

        then:
        result.toString() == expectedDefaultCutUrl
    }

    def "should cut url true when exists"() {
        given:
        def url = "http://test1"
        def cutURL = publicApi.createCutURL(url)

        when:
        def receivedCutURL = publicApi.getCutURL(url)

        then:
        receivedCutURL.toString() == "Some($cutURL)"
    }
}