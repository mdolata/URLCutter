package com.mdolata.urlCutter.dao

import arrow.core.Option

class PairDAO {
    private val list = ArrayList<Pair>()

    fun addNewPair(url: String, cutURL: String) {
        list.add(Pair(url, cutURL))
    }

    // TODO
    // should be use Option
    fun getPairOf(url: String): Option<Pair> {
        return Option.fromNullable(list.find { x -> x.url == url })
    }

    fun getUrl(url: String): String {
        return list.find { x -> x.url == url }?.url ?: ""
    }

    fun getCutURL(cutURL: String): String {
        return list.find { x -> x.cutURL == cutURL }?.cutURL ?: ""
    }

    fun getAll(): List<Pair> {
        return list.toList()
    }
}