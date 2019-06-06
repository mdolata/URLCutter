package com.mdolata.urlCutter.services

import com.mdolata.urlCutter.dao.Properties
import com.mdolata.urlCutter.utils.RandomStringGenerator
import com.mdolata.urlCutter.utils.UrlFormatter


class RandomStringService(private val crudService: CrudService,
                          private val randomStringGenerator: RandomStringGenerator,
                          private val properties: Properties) {

    fun getUniqueCutURL(maximumAttempts: Int): String {

        // TODO that algorithm is not the best
        // think about different thread(s) which will add free cutURLs to queue
        // then this method could easily take first from queue and move on

        if (maximumAttempts < 1) {
            throw RuntimeException("Creating cutURL failed")
        }

        val path = randomStringGenerator.getRandomString(properties.URLLength)
        val cutURL = UrlFormatter.getCutURLFromPath(properties.base, path)
        if (crudService.isCutURLExists(cutURL))
            return getUniqueCutURL(maximumAttempts - 1)
        return cutURL
    }
}