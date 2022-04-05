package com.stannis.dataModel.complexStatementTypes

data class TranslationWithPath(
    val finalTranslation: FinalTranslation,
    val path: String,
)
