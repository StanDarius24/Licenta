package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.parser.sln.VcxprojStructure
import java.util.StringJoiner

data class RepositoryModel(
    override val `$type`: String? = "RepositoryModel",
    val listOfHeaderFiles: ArrayList<ClassOrHeaderWithPath>?,
    val listOfCppFiles: ArrayList<ClassOrHeaderWithPath>?,
    val vcxprojStructure: VcxprojStructure
): Statement
