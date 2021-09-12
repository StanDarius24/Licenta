package com.stannis.json

import com.google.gson.Gson
class JsonBuilder {
    companion object {
        fun classToJson(theClass: Class<*>?): String {
            return Gson().toJson(theClass)
        }
    }
}