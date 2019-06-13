package com.mdolata.urlCutter.dao

import arrow.core.Option
import arrow.core.toOption

class PairDAO {
    private val list = ArrayList<Pair>()

    fun addNewPair(url: String, cutURL: String) {
        list.add(Pair(url, cutURL))
    }

    fun getPairOf(url: String): Option<Pair> {
        return Option.fromNullable(list.find { x -> x.url == url })
    }

    fun getUrl(url: String): Option<String> {
        return list.find { x -> x.url == url }
                .toOption()
                .map { pair -> pair.url }
    }

    fun getCutURL(cutURL: String): Option<String> {
        return list.find { x -> x.cutURL == cutURL }
                .toOption()
                .map { pair -> pair.cutURL }
    }

    fun getAll(): List<Pair> {
        return list.toList()
    }
}