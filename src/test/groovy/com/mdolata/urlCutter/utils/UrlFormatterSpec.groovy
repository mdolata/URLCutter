package com.mdolata.urlCutter.utils

import spock.lang.Specification
import spock.lang.Subject


class UrlFormatterSpec extends Specification {

    @Subject
    def urlFormatter

    void setup() {
        urlFormatter = new UrlFormatter()
    }

    def "GetCutURLFromPath"() {
        when:
        def result = urlFormatter.getCutURLFromPath(base, path)
        then:
        result == expectedResult

        where:
        base           | path    | expectedResult
        "test"         | "test"  | "test/test"
        "test123"      | "test"  | "test123/test"
        "TESt"         | "test"  | "TESt/test"
        "www.base.com" | "dummy" | "www.base.com/dummy"
    }
}
