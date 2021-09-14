package com.stannis.dataModel

data class Method(
    var content: String?,
    var returnType: String?,
    var name: String?,
    var antet: ArrayList<Declaration>? = ArrayList(),
    var declarations: ArrayList<Declaration>? = ArrayList(),
    var statement: ArrayList<Statement>? = ArrayList()
) {

}
