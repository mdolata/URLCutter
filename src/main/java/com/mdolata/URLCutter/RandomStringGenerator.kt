package com.mdolata.URLCutter


object RandomStringGenerator {
    private val alphabet = createAlphabet()

    fun getRandomString(length: Int): String {
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