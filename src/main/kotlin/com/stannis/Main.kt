package com.stannis

import com.stannis.dataModel.dataType
import com.stannis.parser.reader.Parser
import com.stannis.parser.reader.Reader

fun main(args: Array<String>) {
//    val reader = Reader()
//    val data = reader.readFileAsLinesUsingBufferedReader("C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\main.cpp")
//    println(data)
    val parser: Parser = Parser()
    parser.test()
    println("DAWDSa")
    dataType.printData()
}