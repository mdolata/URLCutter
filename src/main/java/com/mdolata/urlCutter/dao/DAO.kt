package com.mdolata.urlCutter.dao


class PairDAO {
    private val list = ArrayList<Pair>()

    fun addNewPair(url: String, cutURL: String) {
        list.add(Pair(url, cutURL))
    }

    fun isURLExists(url: String): Boolean {
        return list.find { x -> x.url == url } != null
    }

    fun getPairOf(url: String): Pair? {
        return list.find { x -> x.url == url }
    }

    fun getCutURLExists(cutURL: String): Boolean {
        return list.find { x -> x.cutURL == cutURL } != null
    }

    fun getAll(): List<Pair> {
        return list.toList()
    }
}