package com.stannis.parser.sln

import com.stannis.parser.fileHandler.DirReader
import com.stannis.parser.fileHandler.OperatingSystem
import java.nio.file.Files
import java.nio.file.Path

object CheckForUnusedClasses {
    fun check(mapOfData: Map<SlnStructure, List<VcxprojStructure>>) {
        mapOfData.forEach { (slnStructure, vcxprojStructures) ->
            run {
                vcxprojStructures.forEach { vcxprojStructure ->
                    run {
                        vcxprojStructure
                            .path
                            .split(OperatingSystem.getSeparator())
                            .dropLast(1)
                            .joinToString(OperatingSystem.getSeparator())
                        var list = ArrayList<String>()
                        if (vcxprojStructure.listOfHeaderFiles != null) {
                            list = (list + (vcxprojStructure.listOfHeaderFiles as ArrayList<String>)) as ArrayList<String>
                        }
                        if (vcxprojStructure.listOfCppFiles != null) {
                            list = (list + (vcxprojStructure.listOfCppFiles  as ArrayList<String>)) as ArrayList<String>
                        }
                        val unusedFiles = getUnusedClasses(
                            DirReader.getAllFilesInResources(
                                vcxprojStructure
                                    .path
                                    .split(OperatingSystem.getSeparator())
                                    .dropLast(1)
                                    .joinToString(OperatingSystem.getSeparator()),
                                testx()
                            ),
                            list
                        )
                        if(unusedFiles.size != 0) {
                            vcxprojStructure.listOfUnusedFiles = unusedFiles
                        }
                    }
                }
            }
        }
    }

    private fun getUnusedClasses(
        listOfExistentFiles: ArrayList<String>,
        listOfDetectedFiles: ArrayList<String>
    ): ArrayList<String> {
        val list = ArrayList<String>()
        val newExistingList = listOfDetectedFiles.map { element -> element.split(OperatingSystem.getSeparator()).last() }
        listOfExistentFiles.forEach { element ->
            run {
                if (!newExistingList.contains(element.split(OperatingSystem.getSeparator()).last())) {
                    list.add(element.split(OperatingSystem.getSeparator()).last())
                }
            }
        }
        return list
    }

    private fun testx(): (Path) -> Boolean {
        return { it ->
            (it.fileName.toString().contains(".cpp") ||
                it.fileName.toString().contains(".cc") ||
                it.fileName.toString().contains(".c") ||
                it.fileName.toString().contains(".cxx") ||
                it.fileName.toString().contains(".h") ||
                it.fileName.toString().contains(".hh") ||
                it.fileName.toString().contains(".hhp")) && Files.isRegularFile(it)
        }
    }
}
