package com.mdolata.urlCutter.dao


class PairDAO {
    private val list = ArrayList<Pair>()

    fun addNewPair(pair: Pair){
        list.add(pair)
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