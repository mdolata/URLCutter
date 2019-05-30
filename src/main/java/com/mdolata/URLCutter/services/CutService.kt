package com.mdolata.URLCutter.services

import com.mdolata.URLCutter.dao.Pair
import com.mdolata.URLCutter.dao.PairDAO
import com.mdolata.URLCutter.dao.Properties
import com.mdolata.URLCutter.utils.UrlFormatter


class CutService(private val crudService: CrudService,
                 private val randomStringService: RandomStringService,
                 private val db: PairDAO,
                 private val properties: Properties) {


    fun cutURL(url: String): String {
        return if (db.isURLExists(url)) {
            crudService.getCutURL(url)
        } else {
            val uniqueCutURL = randomStringService.getUniqueCutURL(properties.attempts)
            db.addNewPair(Pair(url, uniqueCutURL))
            uniqueCutURL
        }
    }

    fun createCustomCutURL(url: String, customUrl: String): String {

        val cutURL = UrlFormatter.getCutURLFromPath(properties.base, customUrl)

        val isCutURLExists = crudService.isCutURLExists(cutURL)
        val isPairExists = isPairExists(url, cutURL)

        if (isCutURLExists) {
            if (!isPairExists)
                throw RuntimeException("cut url already exists")
        } else {
            db.addNewPair(Pair(url, cutURL))
        }
        return cutURL
    }


    private fun isPairExists(url: String, cutURL: String): Boolean {
        return db.getAll().count { pair -> pair.url == url && pair.cutURL == cutURL } == 1
    }
}