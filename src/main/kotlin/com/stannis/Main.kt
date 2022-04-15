package com.stannis

import com.stannis.parser.fileHandler.JsonWriter
import com.stannis.parser.fileHandler.OperatingSystem
import com.stannis.parser.fileHandler.Parser
import com.stannis.parser.json.JsonBuilder
import com.stannis.parser.sln.SlnParser
import com.stannis.parser.visitor.ASTVisitorOverride

fun main() {
    val path =
        "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\project64-develop\\Project64.sln"
    val list =
        SlnParser.locateAllSlnFiles(
            "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\project64-develop"
        )
    val projectPath =
        path.subSequence(0, path.lastIndexOf(OperatingSystem.getSeparator())).toString()

//        val parser = Parser()
//        val astVisitorOverride = ASTVisitorOverride()
//        list!!.forEach { element -> run {
//            SlnParser.solveSln(element)
//            parser.lookUpForVcxProjAndParseHeaderFiles(astVisitorOverride, projectPath)
//            JsonWriter.writeData()
//            println()
//        } }

    val final =
        JsonBuilder.decodeComplexObject(
            "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\result\\project64-develop\\result.json"
        )
    println()

    //    LinkClassDeclarationsService.declarationsToClass()
    //    DeclarationLinkToClassService.link()
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
