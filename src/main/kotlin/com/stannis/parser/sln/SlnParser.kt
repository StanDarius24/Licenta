package com.stannis.parser.sln

import com.stannis.parser.fileHandler.Reader

object SlnParser {
    private lateinit var startSplitString: String
    private var slnDataList: List<SlnStructure>? = null
    fun solveSln(pathToSln: String) {
        val list: ArrayList<String> = ArrayList()
        val file = Reader.readFileAsLinesUsingBufferedReader(pathToSln)
        val sss = file.split("Project(\"{").filter { element -> element.contains("EndProject") } as ArrayList
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
                            element.split(
                                "ProjectSection(ProjectDependencies) = postProject\r\n\t\t"
                            )[1]
                                .split("\tEndProjectSection")[0]
                        dependencylist =
                            (dependency.split("{") as ArrayList).filter { elem ->
                                elem.contains("} = ")
                            }
                        dependencylist = dependencylist.map { elem -> elem.removeSuffix("} = ") }
                        dependencylist.iterator().forEachRemaining { iter ->
                            run { map += Pair(iter, null) }
                        }
                    }
                    (slnDataList!! as ArrayList).add(SlnStructure(token, name, pathx, alias, map))
                }
            }
        }
        VcxprojParser.solveProjectComplexity(slnDataList, path)
    }

    private fun solveWrongRegex(value: String, file: String, list: ArrayList<String>) {
        list.add(file.split(startSplitString)[1].split(value)[0])
    }
}
