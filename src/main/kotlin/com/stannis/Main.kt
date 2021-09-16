package com.stannis

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.stannis.dataModel.Declaration
import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.parser.reader.Parser
import com.stannis.services.forDataModel.UnitService
import javax.swing.plaf.nimbus.State

fun main() {
//    val reader = Reader()
//    val data = reader.readFileAsLinesUsingBufferedReader("C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\main\\resources\\c++\\main.cpp")
//    println(data)

    val parser = Parser()
    parser.test()

//    var arr1 = ArrayList<Declaration>()
//    arr1.add(Declaration("buna","eu sunt declararea 1", true))
//    arr1.add(Declaration("buna","aaaaaaaaa", false))
//
//    var arr2 = ArrayList<Declaration>()
//    arr2.add(Declaration("buna","eu sunt declararea 1", true))
//    arr2.add(Declaration("buna","eu sunt declararea 1", true))
//
//    var arr3 = ArrayList<Statement>()
//    var arrayList = ArrayList<String>()
//    arrayList.add("DA")
//    arrayList.add("Nu")
//    arr3.add(Statement("DADA", arrayList))
//    arr3.add(Statement("DADA", arrayList))
//
//
//    var meth = Method("Salut", "Buna", "Cf", arr1, arr2, arr3)
//    var gson = GsonBuilder().setPrettyPrinting().create()
//    var jsonString = gson.toJson(meth)
//    println(jsonString)

}