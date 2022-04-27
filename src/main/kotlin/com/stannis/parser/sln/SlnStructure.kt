package com.stannis.parser.sln

import com.stannis.dataModel.Statement

data class SlnStructure(
    override val `$type`: String? = "SlnStructure",
    var token: String,
    var name: String,
    var path: String,
    var alias: String,
    var antebuild: Map<String, String?>
): Statement
