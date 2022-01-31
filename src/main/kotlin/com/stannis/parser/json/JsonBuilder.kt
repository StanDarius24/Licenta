package com.stannis.parser.json

import com.google.gson.GsonBuilder
import com.stannis.dataModel.PrimaryBlock
import com.stannis.dataModel.statementTypes.FunctionDefinition

class JsonBuilder {
    fun createJson(primaryBlock: PrimaryBlock): String{
        val gson = GsonBuilder().setPrettyPrinting().create()
        val jsonString = gson.toJson(primaryBlock)
        return jsonString
    }
    fun createJson(list: ArrayList<FunctionDefinition>?): String? {
        var jsonString: String? = null
        if(list != null) {
            val gson = GsonBuilder().setPrettyPrinting().create()
            list.iterator().forEachRemaining { element -> run {
                jsonString = jsonString + gson.toJson(element)
            } }
        }
        return jsonString
    }
}