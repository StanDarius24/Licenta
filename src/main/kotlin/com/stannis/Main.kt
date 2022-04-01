package com.stannis

import com.stannis.parser.fileHandler.OperatingSystem
import com.stannis.parser.fileHandler.Parser
import com.stannis.parser.sln.SlnParser
import com.stannis.parser.visitor.ASTVisitorOverride

fun main() {
    OperatingSystem.getOPSystem()

    SlnParser.solveSln(
        "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\project64-develop\\Project64.sln"
    )
    val parser = Parser()
    val astVisitorOverride = ASTVisitorOverride()
    parser.lookUpForVcxProjAndParseHeaderFiles(astVisitorOverride)
//    parser.parseHeaderFiles(
//        "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\project64-develop\\Project64.sln",
//        astVisitorOverride
//    )
    //
    // parser.parseHeaderFiles("C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\rec\\test-C", astVisitorOverride)
    //    parser.justDoSmth(
    //        "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\rec\\test-C",
    // astVisitorOverride
    //    )
}
