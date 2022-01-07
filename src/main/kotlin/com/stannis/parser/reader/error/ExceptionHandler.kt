package com.stannis.parser.reader.error

import com.stannis.parser.reader.Reader
import java.io.File
import java.io.FileNotFoundException
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
                data = data.replace(text, "parser$contor;\n")
                mapOfProblemStatement = mapOfProblemStatement + mapOf(text to "parser$contor;\n")
                contor++
            } catch (ffe: FileNotFoundException) {
                println(ffe.stackTrace)
            } catch (ioe: IOException) {
                println(ioe.stackTrace)
            }
        }
        return data.toCharArray()
    }

    fun rewritefile(text: String, filepath: String): CharArray? {
        if(!text.contains("parser")) {
            var data = ""
            if (text != "") {
                try {
                    val reader = Reader()
                    data = reader.readFileAsLinesUsingBufferedReader(filepath)
                    data = data.replace(text, "//parser$contor;\n")
                    mapOfProblemStatement = mapOfProblemStatement + mapOf(text to "//parser$contor;\n")
                    contor++
                    val file = File(filepath)
                    file.delete()
                    file.writeText(data)
                } catch (ffe: FileNotFoundException) {
                    println(ffe.stackTrace)
                } catch (ioe: IOException) {
                    println(ioe.stackTrace)
                }
            }
            return data.toCharArray()
        }
        return null
    }
}