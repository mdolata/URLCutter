package com.mdolata.URLCutter

import com.mdolata.URLCutter.utils.UrlFormatter


class RandomStringService (private val crudService: CrudService, private val properties: Properties) {

    fun getUniqueCutURL(maximumAttempts: Int): String {

        // TODO that algorithm is not the best
        // think about different thread(s) which will add free cutURLs to queue
        // then this method could easily take first from queue and move on

        if (maximumAttempts == 0) {
            throw RuntimeException("Creating cutURL failed")
        }

        val path = RandomStringGenerator.getRandomString(properties.URLLength)
        val cutURL = UrlFormatter.getCutURLFromPath(properties.base, path)
        if (crudService.isCutURLExists(cutURL))
            return getUniqueCutURL(maximumAttempts - 1)
        return cutURL
    }
}