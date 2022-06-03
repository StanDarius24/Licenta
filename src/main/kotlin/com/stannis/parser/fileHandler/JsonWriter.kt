package com.stannis.parser.fileHandler

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.parser.checkSum.CheckSum
import com.stannis.parser.json.JsonBuilder

object JsonWriter {

    fun writeData(element: String, joinToString: String) {
        val stringToWrite = JsonBuilder.createComplexJson(ProjectVcxprojComplexRegistry.parsedFiles)
        val checkSum = stringToWrite?.let { CheckSum.getMD5EncryptedString(it) }
        val fileToWrite = DirReader.createfile("$element{$checkSum}.json", joinToString)
            if (stringToWrite != null) {
                fileToWrite?.bufferedWriter().use { out -> out!!.write(stringToWrite) }
            }
        }
}

