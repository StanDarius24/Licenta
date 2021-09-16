package com.stannis.json

import com.google.gson.GsonBuilder
import com.stannis.dataModel.Method

class JsonBuilder {
    fun createJson(method: Method): String{
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(method)
        return jsonString
    }
}