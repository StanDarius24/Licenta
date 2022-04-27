package com.stannis.parser.sln

import com.stannis.parser.fileHandler.DirReader
import com.stannis.parser.fileHandler.LogicHandler
import com.stannis.parser.fileHandler.OperatingSystem
import com.stannis.parser.fileHandler.Reader
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path

object VcxprojParser {

    private var listOfCpp = listOf(".cpp", ".cc", ".c", ".cxx")
    private var listOfH = listOf(".h", ".hh", ".hhp")

    var mapOfData: Map<SlnStructure, List<VcxprojStructure>> = emptyMap()

    fun solveProjectComplexity(list: List<SlnStructure>?, path: String) {
        val projectPath =
            path.subSequence(0, path.lastIndexOf(OperatingSystem.getSeparator())).toString()
        list!!.iterator().forEachRemaining { element ->
            run {
                if (element.path.contains(".vcxproj") || element.path.contains(".vcproj")) {
                    if (OperatingSystem.getOPSystem().equals("Linux")) {
                        element.path = element.path.replace("\\", "/")
                    }
                    try {
                        val absolutPath =
                            projectPath + OperatingSystem.getSeparator() + element.path
                        if (Reader.checkIfFileExist(absolutPath)) {
                            createVcxproj(getFileData(absolutPath), absolutPath, element)
                        } else {
                            val listw = listOf<String>(element.path.split(".")[0], "vcxproj")
                            val datax = listw.joinToString(".")
                            val pathToLostVcxproj =
                                DirReader.getAllFilesInResources(
                                    LogicHandler.projectPath,
                                    testx(datax)
                                )
                            if (!pathToLostVcxproj.isEmpty()) {
                                createVcxproj(
                                    getFileData(pathToLostVcxproj[0]),
                                    absolutPath,
                                    element
                                )
                            }
                        }
                    } catch (e: FileNotFoundException) {
                        println(e.printStackTrace())
                    }
                } else {
                    val listOfDirectoryVcxproj = ArrayList<String>()
                    File(
                            path.subSequence(0, path.lastIndexOf(OperatingSystem.getSeparator()))
                                .toString()
                        )
                        .walk()
                        .forEach { elem ->
                            if (elem.toString()
                                    .contains(element.path.replace("\\s".toRegex(), "")) &&
                                    elem.toString().contains(".vcxproj") &&
                                    !elem.toString().contains(".user") &&
                                    !elem.toString().contains(".filters")
                            ) {
                                listOfDirectoryVcxproj.add(elem.toString())
                            }
                        }
                    listOfDirectoryVcxproj.iterator().forEachRemaining { elementList ->
                        run { createVcxproj(getFileData(elementList), elementList, element) }
                    }
                }
            }
        }
        CheckForUnusedClasses.check(mapOfData)
    }

    private fun createVcxproj(text: String, path: String, slnStructure: SlnStructure) {
        var bool1 = false
        val list =
            text.split("<ItemGroup>").filter { element ->
                element.contains("<ClInclude ") || element.contains("<ClCompile ")
            }
        val structure = VcxprojStructure(path = path, listOfCppFiles = null, listOfHeaderFiles = null, listOfUnusedFiles = null, listOfIncludedModules = null)
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
            listx.forEach { element ->
                run {
                    var boolean1 = false
                    listOfH.forEach { termination ->
                        run {
                            if (element.contains(termination)) {
                                boolean1 = true
                                if (structure.listOfHeaderFiles == null) {
                                    structure.listOfHeaderFiles = ArrayList()
                                }
                                (structure.listOfHeaderFiles as ArrayList).add(element)
                            }
                        }
                    }
                    if (!boolean1) {
                        listOfCpp.forEach { termination ->
                            run {
                                if (element.contains(termination)) {
                                    boolean1 = true
                                    if (structure.listOfCppFiles == null) {
                                        structure.listOfCppFiles = ArrayList()
                                    }
                                    if (!structure.listOfCppFiles!!.contains(element)) {
                                        (structure.listOfCppFiles as ArrayList).add(element)
                                    }
                                }
                            }
                        }
                    }
                    if (!boolean1) {
                        println()
                    }
                }
            }
        }
        if (mapOfData[slnStructure] != null && !mapOfData[slnStructure]?.isEmpty()!!) {
            (mapOfData as MutableMap).put(slnStructure, mapOfData[slnStructure]!!.plus(structure))
        } else {
            val listOfStructureX = listOf(structure)
            mapOfData = mutableMapOf(slnStructure to listOfStructureX) + mapOfData
        }
        println(structure)
    }

    private fun solveProjectReference(structure: VcxprojStructure, text: String) {
        val listOfData =
            text.split("ProjectReference Include=").filter { element ->
                element.contains("</ProjectReference>")
            }
        structure.listOfIncludedModules = listOfData.map { elem -> elem.split("\"")[1] }
    }

    private fun solveVcxprojInternalFiles(headerFile: String): List<String> {
        val fileList =
            if (headerFile.split("<ClInclude Include=\"").size > 1) {
                headerFile.split("<ClInclude Include=\"")
            } else {
                headerFile.split("<ClCompile Include=\"")
            }
        return fileList
            .drop(1)
            .filter { element -> checkExtension(element, listOfCpp + listOfH) }
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

    private fun testx(test: String): (Path) -> Boolean {
        return { it -> it.fileName.toString().contains(test) && Files.isRegularFile(it) }
    }
}
