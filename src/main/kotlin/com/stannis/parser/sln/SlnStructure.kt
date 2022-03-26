package com.stannis.parser.sln

data class SlnStructure(
    var token: String,
    var name: String,
    var path: String,
    var alias: String,
    var dependency: Map<String, String?>
)
