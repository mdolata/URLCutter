package com.mdolata.URLCutter


class PublicApi {
    fun isURLExists(url: String): Boolean {
        return false
    }

    fun isCutURLExists(url: String): Boolean {
        return false
    }

    fun cutURL(url: String): String {
        return ""
    }

    fun createCustomCutURL(customUrl: String): String {
        return ""
    }

    fun getAllURLs(): List<String> {
        return listOf()
    }

    fun getAllCutedURLs(): List<String> {
        return listOf()
    }
}