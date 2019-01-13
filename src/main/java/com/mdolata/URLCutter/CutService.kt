package com.mdolata.URLCutter

class CutService(private val properties: Properties) {

    val db = PairDAO()

    fun cutURL(url: String): String {
        return if (isURLExists(url)) {
            getCutURL(url)
        } else {
            val uniqueCutURL = getUniqueCutURL(properties.attempts)
            db.addNewPair(Pair(url, uniqueCutURL))
            uniqueCutURL
        }
    }

    fun isURLExists(url: String): Boolean {
        return db.isURLExists(url)
    }

    fun getCutURL(url: String): String {
        return db.getPairOf(url).cutURL
    }

    fun isCutURLExists(cutURL: String): Boolean {
        return db.getCutURLExists(cutURL)
    }

    private fun getUniqueCutURL(maximumAttempts: Int): String {

        // TODO that algorithm is not the best
        // think about different thread(s) which will add free cutURLs to queue
        // then this method could easily take first from queue and move on

        if (maximumAttempts == 0) {
            throw RuntimeException("Creating cutURL failed")
        }

        val path = RandomStringGenerator.getRandomString(properties.URLLength)
        val cutURL = getCutURLFromPath(path)
        if (isCutURLExists(cutURL))
            return getUniqueCutURL(maximumAttempts - 1)
        return cutURL
    }

    private fun getCutURLFromPath(path: String) = "${properties.base}/$path"

    fun getAllURLs(): List<String> {
        return db.getAll().map { pair -> pair.url }
    }

    fun getAllCutURLs(): List<String> {
        return db.getAll().map { pair -> pair.cutURL }
    }

    fun createCustomCutURL(url: String, customUrl: String): String {

        val cutURL = getCutURLFromPath(customUrl)

        val isCutURLExists = isCutURLExists(cutURL)
        val isPairExists = isPairExists(url, cutURL)

        if (isCutURLExists) {
            if (!isPairExists)
                throw RuntimeException("cut url already exists")
        } else {
            db.addNewPair(Pair(url, cutURL))
        }
        return cutURL
    }

    private fun isPairExists(url: String, cutURL: String): Boolean {
        return db.getAll().count { pair -> pair.url == url && pair.cutURL == cutURL } == 1
    }
}
