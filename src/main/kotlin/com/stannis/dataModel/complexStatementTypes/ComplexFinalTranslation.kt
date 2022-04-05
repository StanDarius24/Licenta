package com.stannis.dataModel.complexStatementTypes

import com.stannis.parser.sln.VcxprojStructure

data class ComplexFinalTranslation(
    val listOfTranslation: ArrayList<TranslationWithPath>,
    val vcxprojStructure: VcxprojStructure
)
