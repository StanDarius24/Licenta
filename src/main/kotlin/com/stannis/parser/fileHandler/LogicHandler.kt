package com.stannis.parser.fileHandler

import com.stannis.callHierarchy.ProjectVcxprojComplexRegistry
import com.stannis.parser.sln.SlnParser
import com.stannis.parser.visitor.ASTVisitorOverride

object LogicHandler {

    lateinit var projectPath: String
    fun run(projectPath: String, listOf: List<String>) {
        this.projectPath = projectPath
        val newPath = projectPath.split(OperatingSystem.getSeparator()) as ArrayList
        val finalx = newPath[newPath.size - 1]
        newPath.remove(finalx)
        newPath.add("result")
        newPath.add(finalx)
        val list = SlnParser.locateAllSlnFiles(projectPath)
        val parser = Parser()
        val astVisitorOverride = ASTVisitorOverride()
//        list!!.forEach { element ->
//            run {
                SlnParser.solveSln(list!![1])
                parser.lookUpForVcxProjAndParseHeaderFiles(astVisitorOverride, projectPath, listOf)
                if (listOf.contains("oop")) {
                    JsonWriter.writeData(
                        list[1].split(OperatingSystem.getSeparator()).last(),
                        newPath.joinToString(OperatingSystem.getSeparator())
                    )
                }
                ProjectVcxprojComplexRegistry.parsedList = ArrayList()
                SlnParser.slnDataList = null
//            }
//        }
    }
}
