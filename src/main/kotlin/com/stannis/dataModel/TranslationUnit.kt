package com.stannis.dataModel

data class TranslationUnit(
    override val `$type`: String? = "TranslationUnit",
    var include: ArrayList<String>?,
    var listOfDeclarations: ArrayList<Statement>?
    ): Statement
