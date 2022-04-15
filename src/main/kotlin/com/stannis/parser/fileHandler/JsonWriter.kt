package com.stannis.parser.fileHandler

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.parser.json.JsonBuilder

object JsonWriter {

    fun writeData() {
        val fileToWrite = DirReader.createfile("result.json")
        fileToWrite.bufferedWriter().use { out ->
                        out.write(JsonBuilder.createComplexJson(ProjectVcxprojComplexRegistry.parsedList))
                    }
    }
}