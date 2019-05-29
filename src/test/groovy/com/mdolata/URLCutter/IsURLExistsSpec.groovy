package com.mdolata.URLCutter

import spock.lang.Specification


class IsURLExistsSpec extends Specification {

    def publicApi

    void setup() {
        def properties = new Properties("mdolata.com", 5, 3)
        def db = new PairDAO()
        def crudService = new CrudService(db)
        def randomStringService = new RandomStringService(crudService, properties)
        def cutService = new CutService(crudService, randomStringService, db, properties)

        publicApi = new PublicApi(crudService, cutService)
    }

    def "should return false when url does not exists"() {
        given:
        def url = "http://test"

        when:
        def isURLExists = publicApi.isURLExists(url)

        then:
        !isURLExists
    }

    def "should return true when url does exists"() {
        given:
        def url = "http://test1"
        publicApi.cutURL(url)

        when:
        def isURLExists = publicApi.isURLExists(url)

        then:
        isURLExists
    }
}
