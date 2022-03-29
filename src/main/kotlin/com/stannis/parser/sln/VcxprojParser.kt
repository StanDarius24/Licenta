package com.stannis.parser.sln

import com.stannis.parser.fileHandler.DirReader
import com.stannis.parser.fileHandler.OperatingSystem
import com.stannis.parser.fileHandler.Reader
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path

object VcxprojParser {

    var listOfEnd = listOf(".h", ".hh", ".hhp", ".cpp", ".cc", ".c", ".cxx")

    var listOfVcxprojStructure: ArrayList<VcxprojStructure>? = null

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
                        createVcxproj(getFileData(projectPath + OperatingSystem.getSeparator() + element.path), projectPath + OperatingSystem.getSeparator() + element.path)
                    } catch (e: FileNotFoundException) {
                        println(e.printStackTrace())
                    }
                } else {
                    val listOfDirectoryVcxproj = DirReader.getFilesFromDir(
                        projectPath + OperatingSystem.getSeparator() + "Source" + OperatingSystem.getSeparator() + element.path.replace(
                            "\\s".toRegex(),
                            ""
                        ), getFilesVcxprojRelated()
                    )
                    println(listOfDirectoryVcxproj)
                    listOfDirectoryVcxproj!!.iterator().forEachRemaining { elementList ->
                        run {
                            createVcxproj(getFileData(elementList), elementList)
                        }
                    }
                }
            }
        }
        println()
    }

    private fun createVcxproj(text: String, path: String) {
        var list = text.split("<ItemGroup>")
            .filter { element -> element.contains("<ClInclude ") || element.contains("<ClCompile ") }
        val structure = VcxprojStructure(path, null, null, null)
        if (list.size > 1) {
            structure.listOfCppFiles = solveVcxprojInternalFiles(list[1])
        }
        if (list.size > 0) {
            structure.listOfHeaderFiles = solveVcxprojInternalFiles(list[0])
        }
        if (listOfVcxprojStructure == null) {
            listOfVcxprojStructure = ArrayList()
        }
        listOfVcxprojStructure!!.add(structure)
        println(structure)
    }


    private fun solveVcxprojInternalFiles(headerFile: String): List<String> {
        var fileList = if (headerFile.split("<ClInclude Include=\"").size > 1) {
            headerFile.split("<ClInclude Include=\"")
        } else {
            headerFile.split("<ClCompile Include=\"")
        }
        return fileList.drop(1).filter { element -> checkExtension(element, listOfEnd) }
            .map { element -> element.subSequence(0, element.indexOf("\"")).toString() }
//        return headerFile.split("<ClInclude Include=\"").drop(1).let {
//            headerFile.split("<ClCompile Include=\"").drop(1).filter { element -> checkExtension(element, listOfEnd) }
//                .map { element -> element.subSequence(0, element.indexOf("\"")).toString() }
//        }
    }

    private fun checkExtension(text: String, termination: List<String>): Boolean {
        var bool = false;
        termination.iterator().forEachRemaining { term ->
            run {
                if (text.contains(term)) {
                    bool = true
                }
            }
        }
        return bool
    }

    private fun getFileData(finalPath: String): String {
        return Reader.readFileAsLinesUsingBufferedReader(finalPath)
    }
}