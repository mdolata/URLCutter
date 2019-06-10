package com.mdolata.urlCutter.services

import com.mdolata.urlCutter.dao.PairDAO

class CrudService(private val db: PairDAO) {

    fun createNewPair(url: String, cutURL: String) {
        db.addNewPair(url, cutURL)
    }

    //TODO
    // should return object with error nor string
    // either?
    fun getCutURL(url: String): String {
        return db.getPairOf(url)?.cutURL ?: "empty"
    }

    fun isURLExists(url: String): Boolean {
        return db.isURLExists(url)
    }

    fun isCutURLExists(cutURL: String): Boolean {
        return db.getCutURLExists(cutURL)
    }

    fun getAllURLs(): List<String> {
        return db.getAll().map { pair -> pair.url }
    }

    fun getAllCutURLs(): List<String> {
        return db.getAll().map { pair -> pair.cutURL }
    }

    fun isPairExists(url: String, cutURL: String): Boolean {
        return (db.getPairOf(url) ?: return false).cutURL == cutURL
    }
}