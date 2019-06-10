package com.mdolata.urlCutter.utils

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll


class RandomStringGeneratorSpec extends Specification {

    @Subject
    def randomStringGenerator

    void setup() {
        randomStringGenerator = new RandomStringGenerator()
    }

    @Unroll
    def "should return random string with defined length"() {
        when:
        def result = randomStringGenerator.getRandomString(length)

        then:
        result.size() == length

        where:
        id | length
        1  | 1
        2  | 2
        3  | 5
        4  | 10
        5  | 0
        6  | 0
    }

    def "should be set an alphabet after construct"() {
        given:
        def lowerCaseLettersCount = 26
        def upperCaseLettersCount = 26
        def digitsCount = 10

        def expectedAlphabetSize = lowerCaseLettersCount + upperCaseLettersCount + digitsCount

        expect:
        RandomStringGenerator.alphabet.size() == expectedAlphabetSize

    }
}
