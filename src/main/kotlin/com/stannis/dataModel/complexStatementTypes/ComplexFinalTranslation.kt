package com.stannis.dataModel.complexStatementTypes

import com.stannis.parser.sln.VcxprojStructure

data class ComplexFinalTranslation(
    val listOfHeaderFiles: ArrayList<TranslationWithPath>?,
    val listOfCppFiles: ArrayList<TranslationWithPath>?,
    val vcxprojStructure: VcxprojStructure
)
