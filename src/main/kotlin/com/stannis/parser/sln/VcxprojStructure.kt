package com.stannis.parser.sln

data class VcxprojStructure(
    var path: String,
    var listOfCppFiles: List<String>?,
    var listofIncludedModules: List<String>?
)
