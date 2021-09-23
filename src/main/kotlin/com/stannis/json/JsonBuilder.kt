package com.stannis.json

import com.google.gson.GsonBuilder
import com.stannis.dataModel.Unit

class JsonBuilder {
    fun createJson(method: Unit): String{
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(method)
        return jsonString
    }
}