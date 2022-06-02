package com.stannis.parser.json

import com.google.gson.GsonBuilder
import com.stannis.dataModel.PrimaryBlock
import com.stannis.dataModel.complexStatementTypes.RepositoryModel


object JsonBuilder {
    fun createJson(primaryBlock: PrimaryBlock): String {
        val gson = GsonBuilder().create()
        return gson.toJson(primaryBlock)
    }

    fun createComplexJson(list: List<RepositoryModel>?): String? {
        if (list != null) {
            val gson = GsonBuilder().create()
            return gson.toJson(list)
        }
        return null
    }

}

