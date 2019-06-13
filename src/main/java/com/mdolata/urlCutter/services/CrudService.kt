package com.mdolata.urlCutter.services

import arrow.core.Option
import com.mdolata.urlCutter.dao.PairDAO

class CrudService(private val db: PairDAO) {

    fun createNewPair(url: String, cutURL: String) = db.addNewPair(url, cutURL)

    fun getCutURL(url: String): Option<String> = db.getPairOf(url).map { pair -> pair.cutURL }

    fun isURLExists(url: String): Boolean = db.getUrl(url).isDefined()

    fun isCutURLExists(cutURL: String): Boolean = db.getCutURL(cutURL).isDefined()

    fun getAllURLs(): List<String> = db.getAll().map { pair -> pair.url }

    fun getAllCutURLs(): List<String> = db.getAll().map { pair -> pair.cutURL }

    fun isPairExists(url: String, cutURL: String): Boolean = db.getPairOf(url)
            .filter { pair -> pair.cutURL == cutURL }
            .isDefined()
}