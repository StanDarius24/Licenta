package com.stannis

import com.stannis.parser.fileHandler.OperatingSystem
import com.stannis.parser.fileHandler.Parser
import com.stannis.parser.sln.SlnParser
import com.stannis.parser.visitor.ASTVisitorOverride

fun main() {
    val path =
        "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\project64-develop\\Project64.sln"
    val projectPath = path.subSequence(0, path.lastIndexOf(OperatingSystem.getSeparator())).toString()

    SlnParser.solveSln(path)
    val parser = Parser()
    val astVisitorOverride = ASTVisitorOverride()
    parser.lookUpForVcxProjAndParseHeaderFiles(astVisitorOverride, projectPath)
    //    parser.parseHeaderFiles(
    //
    // "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\project64-develop\\Project64.sln",
    //        astVisitorOverride
    //    )
    //
    // parser.parseHeaderFiles("C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\rec\\test-C", astVisitorOverride)
    //    parser.justDoSmth(
    //        "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\rec\\test-C",
    // astVisitorOverride
    //    )
}
