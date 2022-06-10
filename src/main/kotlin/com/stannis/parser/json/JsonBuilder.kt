package com.stannis.parser.json

import com.google.gson.GsonBuilder
import com.stannis.dataModel.TranslationUnit
import com.stannis.dataModel.complexStatementTypes.RepositoryModel


object JsonBuilder {
    fun createJson(translationUnit: TranslationUnit): String? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(translationUnit)
    }

    fun createComplexJson(list: List<RepositoryModel>?): String? {
        if (list != null) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            return gson.toJson(list)
        }
        return null
    }

}

