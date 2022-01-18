package com.stannis.parser.fileHandler

import java.io.File

class Reader {
    fun readFileAsLinesUsingBufferedReader(fileName: String): String
            = File(fileName).readText(Charsets.UTF_8)
}