package com.mdolata.URLCutter

class CutService(private val properties: Properties) {
    private val alphabet = createAlphabet()

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
        return "${properties.base}/${getRandomString(properties.URLLength)}"
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

    //TODO move getRandomString to its own class
    private fun getRandomString(length: Int): String {
        var result = ""
        for (i in 1..length) {
            result += alphabet.random()
        }
        return result
    }

    private fun createAlphabet(): ArrayList<Char> {
        val lowerCase = CharRange('a', 'z').toList()
        val upperCase = CharRange('A', 'Z').toList()
        val numbers = CharRange('0', '9').toList()
        return ArrayList<Char>().apply {
            addAll(lowerCase)
            addAll(upperCase)
            addAll(numbers)
        }
    }


}
