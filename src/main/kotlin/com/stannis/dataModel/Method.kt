package com.stannis.dataModel

data class Method(
    var content: String?,
    var returnType: String?,
    var name: String?,
    var antet: ArrayList<Declaration>?,
    var declarations: ArrayList<Declaration>?,
    var statement: ArrayList<Statement>?
)
