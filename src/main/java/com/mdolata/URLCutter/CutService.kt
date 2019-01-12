package com.mdolata.URLCutter

class CutService(private val properties: Properties) {

    val db = PairDAO()

    fun cutURL(url: String): String {
        return if (isURLExists(url)) {
            getCutURL(url)
        } else {
            val uniqueCutURL = getUniqueCutURL()
            db.addNewPair(Pair(url, uniqueCutURL))
            uniqueCutURL
        }
    }

    private fun getUniqueCutURL(): String {
        //TODO create uniqueness algorithm
        return "${properties.base}/${RandomStringGenerator.getRandomString(properties.URLLength)}"
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
}
