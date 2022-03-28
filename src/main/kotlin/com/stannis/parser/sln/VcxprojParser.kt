package com.stannis.parser.sln

import com.stannis.parser.fileHandler.DirReader
import com.stannis.parser.fileHandler.OperatingSystem
import com.stannis.parser.fileHandler.Reader
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path

object VcxprojParser {

    private fun getFilesVcxprojRelated(): (Path) -> Boolean {
        return { path ->
            Files.isRegularFile(path) &&
                    path.toString().contains(".vcxproj") &&
                    !path.toString().contains(".filters") &&
                    !path.toString().contains(".user")
        }
    }

    fun solveProjectComplexity(list: List<SlnStructure>?, path: String) {
        println()
        val projectPath = path.subSequence(0, path.lastIndexOf(OperatingSystem.getSeparator())).toString()
        list!!.iterator().forEachRemaining { element ->
            run {
                if (element.path.contains(".vcxproj")) {
                    println()
                    val file = try {
                        getFileData(projectPath + element.path)
                    } catch (e: FileNotFoundException) {
                        println(e.printStackTrace())
                    }
                } else {
                    val listx = DirReader.getFilesFromDir(projectPath + OperatingSystem.getSeparator() + "Source" + OperatingSystem.getSeparator() + element.path.replace("\\s".toRegex(), ""), getFilesVcxprojRelated())
                    println(listx)
                }
            }
        }
    }

    private fun getFileData(finalPath: String): String {
        return Reader.readFileAsLinesUsingBufferedReader(finalPath)
    }
}