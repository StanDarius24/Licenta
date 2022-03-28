package com.stannis.parser.sln

import com.stannis.parser.fileHandler.Reader

object SlnParser {
    private lateinit var startSplitString: String
    private var slnDataList: List<SlnStructure>? = null
    fun solveSln(pathToSln: String) {
        var bool1 = false
        val list: ArrayList<String> = ArrayList()
        val file = Reader.readFileAsLinesUsingBufferedReader(pathToSln)
        val pattern = Regex("Project[a-zA-Z0-9(){}.,\\\\ =\\-\"\t_]+(\\r\\n|\\r|\\n)EndProject")
        val sss = pattern.findAll(file)
        lateinit var oldValue: String
        sss.iterator().forEachRemaining { data ->
            run {
                if (bool1) {
                    bool1 = false
                    solveWrongRegex(data.value, file, list)
                }
                if (data.value == "ProjectSection\r\n" + "EndProject") {
                    startSplitString = oldValue
                    bool1 = true
                } else {
                    list.add(data.value)
                }
                oldValue = data.value
            }
        }
        parseFieldsForSlnFile(list, pathToSln)
    }

    private fun parseFieldsForSlnFile(list: ArrayList<String>, path: String) {
        list.iterator().forEachRemaining { element -> run {
            val map = mutableMapOf<String, String?>()
            val token = element.split("\"")[1]
            val name = element.split("\"")[3]
            val pathx = element.split("\"")[5]
            val alias = element.split("\"")[7]
            if(slnDataList == null) {
                slnDataList = ArrayList()
            }
            if(element.contains("ProjectSection(ProjectDependencies)")) {
                var dependencylist: List<String>?
                val dependency = element.split("ProjectSection(ProjectDependencies) = postProject\r\n\t\t")[1].split("\tEndProjectSection")[0]
                dependencylist = (dependency.split("{") as ArrayList).filter { elem -> elem.contains("} = ") }
                dependencylist = dependencylist.map { elem -> elem.removeSuffix("} = ") }
                dependencylist.iterator().forEachRemaining { iter -> run {
                    map += Pair(iter, null)
                } }
            }
            (slnDataList!! as ArrayList).add(SlnStructure(token, name, pathx, alias, map))
        } }
        VcxprojParser.solveProjectComplexity(slnDataList, path)
    }

    private fun solveWrongRegex(value: String, file: String, list: ArrayList<String>) {
        list.add(file.split(startSplitString)[1].split(value)[0])
    }
}
