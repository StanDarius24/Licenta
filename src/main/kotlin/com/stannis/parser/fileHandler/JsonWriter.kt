package com.stannis.parser.fileHandler

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.parser.json.JsonBuilder

object JsonWriter {

    fun writeData(element: String, joinToString: String) {
        val fileToWrite = DirReader.createfile("$element.json", joinToString)
        fileToWrite?.bufferedWriter()?.use { out ->
            JsonBuilder.createComplexJson(ProjectVcxprojComplexRegistry.parsedFiles)?.let { out.write(it) }
        }
    }
}
