package com.mdolata.urlCutter.services

import com.mdolata.urlCutter.dao.PairDAO


class CrudService(private val db: PairDAO) {

    fun isURLExists(url: String): Boolean {
        return db.isURLExists(url)
    }

    fun getCutURL(url: String): String {
        return db.getPairOf(url).cutURL
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
}