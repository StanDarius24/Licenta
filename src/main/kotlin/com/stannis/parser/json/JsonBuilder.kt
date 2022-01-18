package com.stannis.parser.json

import com.google.gson.GsonBuilder
import com.stannis.dataModel.PrimaryBlock

class JsonBuilder {
    fun createJson(primaryBlock: PrimaryBlock): String{
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(primaryBlock)
        return jsonString
    }
}