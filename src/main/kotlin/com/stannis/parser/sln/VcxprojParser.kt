package com.stannis.parser.sln

import com.stannis.parser.fileHandler.OperatingSystem
import com.stannis.parser.fileHandler.Reader
import java.io.File
import java.io.FileNotFoundException

object VcxprojParser {

    private var listOfEnd = listOf(".h", ".hh", ".hhp", ".cpp", ".cc", ".c", ".cxx")
    private var listOfH = listOf(".h", ".hh", ".hhp")

    var mapOfData: Map<SlnStructure, List<VcxprojStructure>> = emptyMap()

    fun solveProjectComplexity(list: List<SlnStructure>?, path: String) {
        val projectPath = path.subSequence(0, path.lastIndexOf(OperatingSystem.getSeparator())).toString()
        list!!.iterator().forEachRemaining { element ->
            run {

                if (element.path.contains(".vcxproj")) {
                    println()
                    try {
                        createVcxproj(
                            getFileData(projectPath + OperatingSystem.getSeparator() + element.path),
                            projectPath + OperatingSystem.getSeparator() + element.path,
                            element
                        )
                    } catch (e: FileNotFoundException) {
                        println(e.printStackTrace())
                    }
                } else {
                    val listOfDirectoryVcxproj = ArrayList<String>()
                    File(path.subSequence(0, path.lastIndexOf(OperatingSystem.getSeparator())).toString()).walk()
                        .forEach { elem ->
                            if (elem.toString().contains(
                                    element.path.replace(
                                        "\\s".toRegex(),
                                        ""
                                    )
                                ) && elem.toString().contains(".vcxproj")
                                && !elem.toString().contains(".user")
                                && !elem.toString().contains(".filters")
                            ) {
                                listOfDirectoryVcxproj.add(elem.toString())
                            }
                        }
                    listOfDirectoryVcxproj.iterator().forEachRemaining { elementList ->
                        run {
                            createVcxproj(getFileData(elementList), elementList, element)
                        }
                    }
                }
            }
        }
        println()
    }

    private fun createVcxproj(text: String, path: String, slnStructure: SlnStructure) {
        var bool1 = false
        val list = text.split("<ItemGroup>")
            .filter { element -> element.contains("<ClInclude ") || element.contains("<ClCompile ") }
        val structure = VcxprojStructure(path, null, null, null)
        solveProjectReference(structure, text)
        if (list.size > 1) {
            val listx = solveVcxprojInternalFiles(list[1])
            listOfH.forEach { elem ->
                run {
                    if (listx[0].contains(elem)) {
                        bool1 = true
                    }
                }
            }
            if (bool1) {
                structure.listOfHeaderFiles = listx
            } else {
                structure.listOfCppFiles = listx
            }
        }
        if (list.isNotEmpty()) {
            bool1 = false
            val listx = solveVcxprojInternalFiles(list[0])
            listOfH.forEach { elem ->
                run {
                    if (listx[0].contains(elem)) {
                        bool1 = true
                    }
                }
            }
            if (bool1) {
                structure.listOfHeaderFiles = listx
            } else {
                structure.listOfCppFiles = listx
            }
        }
        if (mapOfData[slnStructure] != null && !mapOfData[slnStructure]?.isEmpty()!!) {
            (mapOfData as MutableMap).put(slnStructure, mapOfData[slnStructure]!!.plus(structure))
        } else {
            var listOfStructureX = listOf(structure)
            mapOfData = mutableMapOf(slnStructure to listOfStructureX) + mapOfData
        }
        println(structure)
    }

    private fun solveProjectReference(structure: VcxprojStructure, text: String) {
        var listOfData =
            text.split("ProjectReference Include=").filter { element -> element.contains("</ProjectReference>") }
        structure.listofIncludedModules = listOfData.map { elem -> elem.split("\"")[1] }
    }

    private fun solveVcxprojInternalFiles(headerFile: String): List<String> {
        val fileList = if (headerFile.split("<ClInclude Include=\"").size > 1) {
            headerFile.split("<ClInclude Include=\"")
        } else {
            headerFile.split("<ClCompile Include=\"")
        }
        return fileList.drop(1).filter { element -> checkExtension(element, listOfEnd) }
            .map { element -> element.subSequence(0, element.indexOf("\"")).toString() }
    }

    private fun checkExtension(text: String, termination: List<String>): Boolean {
        var bool = false
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