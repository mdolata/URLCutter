package com.mdolata.urlCutter

import arrow.core.Either
import arrow.core.Option
import com.mdolata.urlCutter.dao.CreationError
import com.mdolata.urlCutter.services.CrudService
import com.mdolata.urlCutter.services.CutService

//TODO
//add test of api in kotlin
class PublicApi(private val crudService: CrudService, private val cutService: CutService) {

    fun isURLExists(url: String): Boolean = crudService.isURLExists(url)

    fun isCutURLExists(cutURL: String): Boolean = crudService.isCutURLExists(cutURL)

    fun createCutURL(url: String): String = cutService.createCutURL(url)

    fun createCustomCutURL(url: String, customUrl: String): Either<CreationError, String> = cutService.createCustomCutURL(url, customUrl)

    fun getAllURLs(): List<String> = crudService.getAllURLs()

    fun getAllCutURLs(): List<String> = crudService.getAllCutURLs()

    fun getCutURL(url: String): Option<String> = crudService.getCutURL(url)
}