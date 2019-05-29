package com.mdolata.URLCutter.dao


data class Properties(val base: String,
                      val URLLength: Int = 6,
                      val attempts: Int = 3)

data class Pair(val url: String,
                val cutURL: String)