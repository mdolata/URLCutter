package com.mdolata.urlCutter.services

import com.mdolata.urlCutter.dao.Pair
import com.mdolata.urlCutter.dao.PairDAO
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll


class CrudServiceSpec extends Specification {

    @Subject
    def crudService
    def db

    void setup() {
        db = new PairDAO()
        crudService = new CrudService(db)
    }


    def "should return false when url does not exists in db"() {
        given:
        def notExistUrl = "www.not.exists.com"

        when:
        def result = crudService.isURLExists(notExistUrl)

        then:
        !result
    }

    def "should return true when url does exists in db"() {
        given:
        def url = "www.exists.com"
        addNewRecord(url, "test")

        when:
        def result = crudService.isURLExists(url)

        then:
        result
    }

    def "should return cut url if exists"() {
        given:
        def url = "www.test.com"
        def expected = "www.exists.com/test"
        addNewRecord(url, expected)

        when:
        def result = crudService.getCutURL(url)

        then:
        result == expected
    }

    def "should return string empty when url does not exist"() {
        given:
        def url = "www.test.com"
        def expected = "empty"

        when:
        def result = crudService.getCutURL(url)

        then:
        result == expected
    }

    def "should return true when cut url exists"() {
        given:
        def cutUrl = "www.exists.com/test"
        addNewRecord("test", cutUrl)

        when:
        def result = crudService.isCutURLExists(cutUrl)

        then:
        result
    }

    def "should return false when cut url does not exists"() {
        given:
        def notExistCutUrl = "www.not.exists.com"

        when:
        def result = crudService.isCutURLExists(notExistCutUrl)

        then:
        !result
    }

    def "should return false when cut url does not exists but exists as a url"() {
        given:
        def url = "www.url.com"
        def cutUrl = "www.exists.com/test"
        addNewRecord(url, cutUrl)

        when:
        def resultUrl = crudService.isCutURLExists(url)
        def resultCutUrl = crudService.isCutURLExists(cutUrl + "not/exists")

        then:
        !resultCutUrl
        !resultUrl
    }

    def "should return empty urls list"() {
        when:
        def resultList = crudService.getAllURLs()

        then:
        resultList == []
    }

    @Unroll
    def "should return list of n urls when n element was added"() {
        given:
        for(int i in 1..numberOfAddedRecords)
            addNewRecord("dummy", "dummy")

        when:
        def resultList = crudService.getAllURLs()

        then:
        resultList.size() == numberOfAddedRecords

        where:
        id | numberOfAddedRecords
        1  | 1
        2  | 5
        3  | 100
        4  | 65
    }

    def "should return empty cutUrls list"() {
        when:
        def resultList = crudService.getAllCutURLs()

        then:
        resultList == []
    }

    @Unroll
    def "should return list of  n cutUrls when n element was added"() {
        given:
        for(int i in 1..numberOfAddedRecords)
            addNewRecord("dummy", "dummy")

        when:
        def resultList = crudService.getAllCutURLs()

        then:
        resultList.size() == numberOfAddedRecords

        where:
        id | numberOfAddedRecords
        1  | 1
        2  | 5
        3  | 100
        4  | 65
    }

    def "should return true when a pair exists"() {
        given:
        def url = "dummy"
        def cutUrl = "dummy"
        addNewRecord(url, cutUrl)

        when:
        def result = crudService.isPairExists(url, cutUrl)

        then:
        result == true
    }

    def "should return false when url exists without pair with cutUrl"() {
        given:
        def url = "dummy"
        def cutUrl = "dummy"
        def notExistsCutUrl = "dummyNotExists"
        addNewRecord(url, cutUrl)

        when:
        def result = crudService.isPairExists(url, notExistsCutUrl)

        then:
        result == false
    }

    def "should return false when cutUrl exists without pair with url"() {
        given:
        def url = "dummy"
        def cutUrl = "dummy"
        def notExistsUrl = "dummyNotExists"
        addNewRecord(url, cutUrl)

        when:
        def result = crudService.isPairExists(notExistsUrl, cutUrl)

        then:
        result == false
    }

    private void addNewRecord(String url, String cutUrl) {
        db.addNewPair(new Pair(url, cutUrl))
    }
}
