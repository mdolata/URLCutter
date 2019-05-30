package com.mdolata.URLCutter.services

import com.mdolata.URLCutter.dao.PairDAO
import com.mdolata.URLCutter.dao.Properties
import net.bytebuddy.ByteBuddy
import net.bytebuddy.agent.ByteBuddyAgent
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy
import net.bytebuddy.implementation.FixedValue
import spock.lang.Specification
import spock.lang.Unroll

import static net.bytebuddy.matcher.ElementMatchers.named


class RandomStringServiceTest2 extends Specification {

    def randomStringService
    def crudService

    void setup() {
        def properties = new Properties("mdolata.com", 5, 3)
        def pairDao = new PairDAO()

        crudService = new CrudService(pairDao)

        randomStringService = new RandomStringService(crudService, properties)
    }

    private void redefineMethodCrudService(String methodName, boolean returnedValue) {
        ByteBuddyAgent.install()
        new ByteBuddy()
                .redefine(CrudService)
                .method(named(methodName))
                .intercept(FixedValue.value(returnedValue))
                .make()
                .load(CrudService.class.getClassLoader(),
                ClassReloadingStrategy.fromInstalledAgent())
    }

    @Unroll
    def "should throw exception when all attempts was exhausted"() {
        setup:
        def methodName = "isCutURLExists"
        def returnedValue = true
        redefineMethodCrudService(methodName, returnedValue)

        when:
        randomStringService.getUniqueCutURL(attempts)

        then:
        RuntimeException ex = thrown()
        ex.message == "Creating cutURL failed"

        where:
        no | attempts
        1  | 0
        2  | 1
        3  | 2
        4  | 5
        5  | 10
        6  | 15
        7  | -1
    }

    @Unroll
    def "should return randomCutUrl when it is unique"() {
        setup:
        def methodName = "isCutURLExists"
        def returnedValue = false
        redefineMethodCrudService(methodName, returnedValue)

        when:
        def result = randomStringService.getUniqueCutURL(attempts)

        then:
        result.size() == 17

        where:
        no | attempts
        1  | 1
        2  | 2
        3  | 5
        4  | 10
        5  | 15
    }
}