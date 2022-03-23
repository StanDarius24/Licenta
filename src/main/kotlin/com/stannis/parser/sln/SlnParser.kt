package com.stannis.parser.sln

import com.stannis.parser.fileHandler.Reader

object SlnParser {
    private var bool1 = false
    private lateinit var startSplitString: String
    var list: ArrayList<String> = ArrayList()
    fun solveSln(pathToSln: String) {
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
        println()
    }

    private fun solveWrongRegex(value: String, file: String, list: ArrayList<String>) {
        list.add(file.split(startSplitString)[1].split(value)[0])
    }
}
