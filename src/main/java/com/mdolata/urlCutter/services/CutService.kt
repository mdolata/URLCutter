package com.mdolata.urlCutter.services

import arrow.core.getOrElse
import com.mdolata.urlCutter.dao.Properties
import com.mdolata.urlCutter.utils.UrlFormatter


class CutService(private val crudService: CrudService,
                 private val randomStringService: RandomStringService,
                 private val properties: Properties) {


    fun createCutURL(url: String): String {
        return crudService.getCutURL(url)
                .getOrElse {
                    val uniqueCutURL = randomStringService.getUniqueCutURL(properties.attempts)
                    crudService.createNewPair(url, uniqueCutURL)
                    uniqueCutURL
                }
    }

    //TODO
    // refactor is needed
    fun createCustomCutURL(url: String, customUrl: String): String {
        /*
        *  0. few custom url can point to one url
        *  1. customUrl -> url
        *  2. check customUrl exists
        *    2.1 if so check if it is duplicated
        *    2.1.1 if yes return error
        *    2.1.2 otherwise return cutUrl
        *  3. if not create new pair
        *  4. return new cutUrl
        */


        val cutURL = UrlFormatter.getCutURLFromPath(properties.base, customUrl)

        val isCutURLExists = crudService.isCutURLExists(cutURL)
//        val isPairExists = crudService.isPairExists(url, createCutURL)

        if (isCutURLExists) {
//            if (!isPairExists)
              throw RuntimeException("cut url already exists")
        } else {
            crudService.createNewPair(url, cutURL)
        }
        return cutURL
    }
}
