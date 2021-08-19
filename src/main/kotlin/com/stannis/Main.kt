package com.stannis

import com.stannis.parser.reader.Reader

fun main(args: Array<String>) {
    val reader = Reader()
    val data = reader.readFileAsLinesUsingBufferedReader("C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\main.cpp")
    println(data)
}