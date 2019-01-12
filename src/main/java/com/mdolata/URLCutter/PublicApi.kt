package com.mdolata.URLCutter

import java.util.*


class PublicApi(val cutService: CutService) {

    fun isURLExists(url: String): Boolean {
        return cutService.isURLExists(url)
    }

    fun isCutURLExists(cutURL: String): Boolean {
        return cutService.isCutURLExists(cutURL)
    }

    fun cutURL(url: String): String {
        return cutService.cutURL(url)
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

    fun getCutURL(url: String): String {
        return cutService.getCutURL(url)
    }
}