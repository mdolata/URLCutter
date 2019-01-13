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

        if (maximumAttempts == 0){
            throw RuntimeException("Creating cutURL failed")
        }

        val cutURL = "${properties.base}/${RandomStringGenerator.getRandomString(properties.URLLength)}"
        if (isCutURLExists(cutURL))
            return getUniqueCutURL(maximumAttempts - 1)
        return cutURL
    }

    fun getAllURLs(): List<String> {
        return db.getAll().map { pair -> pair.url }
    }

    fun getAllCutURLs(): List<String> {
        return db.getAll().map { pair -> pair.cutURL }
    }
}
