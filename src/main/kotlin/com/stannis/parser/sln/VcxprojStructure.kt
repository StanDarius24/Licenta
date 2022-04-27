package com.stannis.parser.sln

data class VcxprojStructure(
    var path: String,
    var listOfCppFiles: List<String>?,
    var listOfHeaderFiles: List<String>?,
    var listofIncludedModules: List<String>?,
    var listOfUnusedFiles: List<String>?
)
