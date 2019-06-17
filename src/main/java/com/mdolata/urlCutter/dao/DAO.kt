package com.mdolata.urlCutter.dao

import arrow.core.Option
import arrow.core.toOption

class PairDAO {
    private val list = mutableListOf<Pair>()

    fun addNewPair(url: String, cutURL: String) = list.add(Pair(url, cutURL))

    //TODO it could return few values
    fun getPairOf(url: String): Option<Pair> = Option.fromNullable(list.find { x -> x.url == url })

    fun getUrl(url: String): Option<String> = list.find { x -> x.url == url }
            .toOption()
            .map { pair -> pair.url }

    fun getCutURL(cutURL: String): Option<String> = list.find { x -> x.cutURL == cutURL }
            .toOption()
            .map { pair -> pair.cutURL }

    fun getAll(): List<Pair> = list
}