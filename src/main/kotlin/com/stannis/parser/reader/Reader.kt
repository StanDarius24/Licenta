package com.stannis.parser.reader

import java.io.File

class Reader {
    fun readFileAsLinesUsingBufferedReader(fileName: String): String
            = File(fileName).readText(Charsets.UTF_8)
}