package com.mdolata.urlCutter.services

import com.mdolata.urlCutter.dao.PairDAO
import com.mdolata.urlCutter.dao.Properties
import com.mdolata.urlCutter.utils.UrlFormatter


class CutService(private val crudService: CrudService,
                 private val randomStringService: RandomStringService,
                 private val db: PairDAO,
                 private val properties: Properties) {


    fun cutURL(url: String): String {
        return if (crudService.isURLExists(url)) {
            crudService.getCutURL(url)
        } else {
            val uniqueCutURL = randomStringService.getUniqueCutURL(properties.attempts)
            crudService.createNewPair(url, uniqueCutURL)
            uniqueCutURL
        }
    }

    fun createCustomCutURL(url: String, customUrl: String): String {

        val cutURL = UrlFormatter.getCutURLFromPath(properties.base, customUrl)

        val isCutURLExists = crudService.isCutURLExists(cutURL)
        val isPairExists = crudService.isPairExists(url, cutURL)

        if (isCutURLExists) {
            if (!isPairExists)
                throw RuntimeException("cut url already exists")
        } else {
            crudService.createNewPair(url, cutURL)
        }
        return cutURL
    }
}
