package com.stannis.dataModel

object DataType {
   private val declarations = ArrayList<String>()

   fun add(data: String) {
      declarations.add(data)
   }

   fun printData() {
      declarations.forEach{ data -> println(data) }
   }
}