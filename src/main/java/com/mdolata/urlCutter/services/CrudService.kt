package com.mdolata.urlCutter.services

import arrow.core.Option
import com.mdolata.urlCutter.dao.PairDAO

class CrudService(private val db: PairDAO) {

    fun createNewPair(url: String, cutURL: String) {
        db.addNewPair(url, cutURL)
    }

    //TODO
    // should return object with error nor string
    // either?
    fun getCutURL(url: String): Option<String> {
        return db.getPairOf(url)
                .map { pair -> pair.cutURL }
    }

    fun isURLExists(url: String): Boolean {
        return db.getUrl(url).isNotEmpty()
    }

    fun isCutURLExists(cutURL: String): Boolean {
        return db.getCutURL(cutURL).isNotEmpty()
    }

    fun getAllURLs(): List<String> {
        return db.getAll().map { pair -> pair.url }
    }

    fun getAllCutURLs(): List<String> {
        return db.getAll().map { pair -> pair.cutURL }
    }

    fun isPairExists(url: String, cutURL: String): Boolean {
        return db.getPairOf(url)
                .filter { pair -> pair.cutURL == cutURL }
                .isDefined()
    }
}