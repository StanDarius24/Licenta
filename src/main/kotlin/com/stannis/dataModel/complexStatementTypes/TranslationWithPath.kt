package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement

data class TranslationWithPath(
    override val `$type`: String? = "TranslationWithPath",
    val finalTranslation: FinalTranslation,
    val path: String
): Statement
