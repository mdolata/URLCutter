package com.mdolata.urlCutter.services

import arrow.core.Either
import arrow.core.getOrElse
import com.mdolata.urlCutter.dao.CreationError
import com.mdolata.urlCutter.dao.Properties
import com.mdolata.urlCutter.utils.UrlFormatter


class CutService(private val crudService: CrudService,
                 private val randomStringService: RandomStringService,
                 private val properties: Properties) {


    fun createCutURL(url: String): String {
        return crudService.getCutURL(url)
                .getOrElse {
                    crudService.createNewPair(url, randomStringService.getUniqueCutURL(properties.attempts))
                }
    }

    fun createCustomCutURL(url: String, customUrl: String): Either<CreationError, String> {
        val cutURL = UrlFormatter.getCutURLFromPath(properties.base, customUrl)

        return crudService.getPairOf(url, cutURL)
                .map { pair -> Either.right(pair.cutURL) }
                .getOrElse {
                    when {
                        !crudService.isCutURLExists(cutURL) -> Either.right(crudService.createNewPair(url, cutURL))
                        else -> Either.left(CreationError("Pair of ($url, $cutURL) already exists"))
                    }
                }
    }
}
