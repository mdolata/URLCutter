package com.mdolata.URLCutter

import spock.lang.Specification


class GetCutURLSpec extends Specification {
    def publicApi

    void setup() {
        def properties = new Properties("mdolata.com", 5, 3)
        def db = new PairDAO()
        def crudService = new CrudService(db)
        def randomStringService = new RandomStringService(crudService, properties)
        def cutService = new CutService(crudService, randomStringService, db, properties)

        publicApi = new PublicApi(crudService, cutService)
    }

    def "should return false when cut url does not exists"() {
        given:
        def url = "http://test1"

        when:
        publicApi.getCutURL(url)

        then:
        NoSuchElementException ex = thrown()

        ex.message == "Collection contains no element matching the predicate."
    }

    def "should cut url true when exists"() {
        given:
        def url = "http://test1"
        def cutURL = publicApi.cutURL(url)

        when:
        def receivedCutURL = publicApi.getCutURL(url)

        then:
        cutURL == receivedCutURL
    }
}