package com.mdolata.urlCutter

import com.mdolata.urlCutter.services.CrudService
import com.mdolata.urlCutter.services.CutService

class PublicApi(private val crudService: CrudService, private val cutService: CutService) {

    fun isURLExists(url: String): Boolean {
        return crudService.isURLExists(url)
    }

    fun isCutURLExists(cutURL: String): Boolean {
        return crudService.isCutURLExists(cutURL)
    }

    fun cutURL(url: String): String {
        return cutService.cutURL(url)
    }

    fun createCustomCutURL(url: String, customUrl: String): String {
        return cutService.createCustomCutURL(url, customUrl)
    }

    fun getAllURLs(): List<String> {
        return crudService.getAllURLs()
    }

    fun getAllCutURLs(): List<String> {
        return crudService.getAllCutURLs()
    }

    fun getCutURL(url: String): String {
        return crudService.getCutURL(url)
    }
}