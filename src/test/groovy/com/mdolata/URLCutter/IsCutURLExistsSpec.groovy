package com.mdolata.URLCutter

import com.mdolata.URLCutter.dao.PairDAO
import com.mdolata.URLCutter.dao.Properties
import com.mdolata.URLCutter.services.CrudService
import com.mdolata.URLCutter.services.CutService
import com.mdolata.URLCutter.services.RandomStringService
import com.mdolata.URLCutter.utils.RandomStringGenerator
import spock.lang.Specification


class IsCutURLExistsSpec extends Specification {
    def publicApi

    void setup() {
        def properties = new Properties("mdolata.com", 5, 3)
        def db = new PairDAO()
        def crudService = new CrudService(db)
        def stringGenerator = new RandomStringGenerator();
        def randomStringService = new RandomStringService(crudService, stringGenerator, properties)
        def cutService = new CutService(crudService, randomStringService, db, properties)

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
        def cutURL = publicApi.cutURL(url)

        when:
        def isCutURLExists = publicApi.isCutURLExists(cutURL)

        then:
        isCutURLExists
    }
}
