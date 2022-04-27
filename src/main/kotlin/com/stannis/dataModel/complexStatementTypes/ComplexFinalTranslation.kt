package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.parser.sln.VcxprojStructure

data class ComplexFinalTranslation(
    override val `$type`: String? = "ComplexFinalTranslation",
    val listOfHeaderFiles: ArrayList<TranslationWithPath>?,
    val listOfCppFiles: ArrayList<TranslationWithPath>?,
    val vcxprojStructure: VcxprojStructure
): Statement
