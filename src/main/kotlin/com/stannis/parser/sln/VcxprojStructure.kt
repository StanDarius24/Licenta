package com.stannis.parser.sln

import com.stannis.dataModel.Statement

data class VcxprojStructure(
    override val `$type`: String? = "VcxprojStructure",
    var path: String,
    var listOfCppFiles: List<String>?,
    var listOfHeaderFiles: List<String>?,
    var listOfIncludedModules: List<String>?,
    var listOfUnusedFiles: List<String>?
): Statement
