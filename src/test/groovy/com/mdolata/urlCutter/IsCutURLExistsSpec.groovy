package com.mdolata.urlCutter

import com.mdolata.urlCutter.dao.PairDAO
import com.mdolata.urlCutter.dao.Properties
import com.mdolata.urlCutter.services.CrudService
import com.mdolata.urlCutter.services.CutService
import com.mdolata.urlCutter.services.RandomStringService
import com.mdolata.urlCutter.utils.RandomStringGenerator
import spock.lang.Specification


class IsCutURLExistsSpec extends Specification {
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
        def cutURL = "testCut"

        when:
        def isCutURLExists = publicApi.isCutURLExists(cutURL)

        then:
        !isCutURLExists
    }

    def "should return true when cut url exists"() {
        given:
        def url = "http://test1"
        def cutURL = publicApi.createCutURL(url)

        when:
        def isCutURLExists = publicApi.isCutURLExists(cutURL)

        then:
        isCutURLExists
    }
}
