package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement

data class ClassOrHeaderWithPath(
    override val `$type`: String? = "ClassOrHeaderWithPath",
    val classOrHeader: ClassOrHeader,
    val path: String
): Statement
