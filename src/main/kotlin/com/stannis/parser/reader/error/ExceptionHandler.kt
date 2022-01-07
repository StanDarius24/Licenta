package com.stannis.parser.reader.error

import com.stannis.parser.reader.Reader
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.IOException

object ExceptionHandler {
    var contor = 0
    var mapOfProblemStatement: Map<String, String> = mutableMapOf()
    fun handleError(text: String, filepath: String): CharArray {
        var data = ""
        if(text != "") {
            try {
                val reader = Reader()
                data = reader.readFileAsLinesUsingBufferedReader(filepath)
                data = data.replace(text, "%%%$contor%%%PARSER")
                mapOfProblemStatement = mapOfProblemStatement + mapOf(text to "%%%$contor%%%PARSER")
                contor++
            } catch (ffe: FileNotFoundException) {
                println(ffe.stackTrace)
            } catch (ioe: IOException) {
                println(ioe.stackTrace)
            }
        }
        return data.toCharArray()
    }
}