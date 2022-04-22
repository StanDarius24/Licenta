package com.stannis.parser.sln

import com.stannis.parser.fileHandler.DirReader
import com.stannis.parser.fileHandler.Reader
import java.nio.file.Files
import java.nio.file.Path

object SlnParser {
    var slnDataList: List<SlnStructure>? = null

    fun solveSln(pathToSln: String) {
        val file = Reader.readFileAsLinesUsingBufferedReader(pathToSln)
        val sss =
            file.split("Project(\"{").filter { element -> element.contains("EndProject") } as
                ArrayList
        parseFieldsForSlnFile(sss, pathToSln)
    }

    private fun parseFieldsForSlnFile(list: ArrayList<String>, path: String) {
        list.iterator().forEachRemaining { element ->
            run {
                if (!element.contains("ProjectSection(SolutionItems)")) {
                    val map = mutableMapOf<String, String?>()
                    val token = element.split("\"")[0]
                    val name = element.split("\"")[2]
                    val pathx = element.split("\"")[4]
                    val alias = element.split("\"")[6]
                    if (slnDataList == null) {
                        slnDataList = ArrayList()
                    }
                    if (element.contains("ProjectSection(ProjectDependencies)")) {
                        var dependencylist: List<String>?
                        val dependency =
                            element.split("ProjectSection(ProjectDependencies) = postProject")[1]
                                .split("\tEndProjectSection")[0]
                        if (dependency.contains("{") && dependency.contains("} = ")) {
                            dependencylist =
                                (dependency.split("{") as ArrayList).filter { elem ->
                                    elem.contains("} = ")
                                }
                            dependencylist =
                                dependencylist.map { elem -> elem.removeSuffix("} = ") }
                            dependencylist.iterator().forEachRemaining { iter ->
                                run { map += Pair(iter, null) }
                            }
                        }
                    }
                    (slnDataList!! as ArrayList).add(SlnStructure(token, name, pathx, alias, map))
                }
            }
        }
        VcxprojParser.solveProjectComplexity(slnDataList, path)
    }

    fun locateAllSlnFiles(pathToProject: String): List<String> {
        return DirReader.getAllFilesInResources(pathToProject, testx())
    }

    private fun testx(): (Path) -> Boolean {
        return { it -> it.fileName.toString().contains(".sln") && Files.isRegularFile(it) }
    }
}
