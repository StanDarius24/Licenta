package com.stannis.function

object ExternalRegistry {

    var listOfExternal: ArrayList<String>? = null

    fun addExternal(extern :String) {
        if (listOfExternal == null) {
            listOfExternal = ArrayList()
        }
        listOfExternal!!.add(extern)
    }

}