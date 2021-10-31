package com.stannis.parser.reader

import java.io.File
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths

class DirReader {

    companion object {
        val folder = "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\result\\"
        fun getAllFilesInResources(): ArrayList<String> {
            val list = ArrayList<String>()
            val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
            val resourcesPath = Paths.get(projectDirAbsolutePath, "/src/main/resources")
            Files.walk(resourcesPath)
                .filter { item -> Files.isRegularFile(item) }
                .forEach { item -> list.add(item.toString())}
            return list
        }

        fun makedir(path: String) {
            val pathNew = folder + path
            val dir = File(pathNew)
            if(!dir.exists()) {
                dir.mkdirs()
            }
        }

        fun createfile(p: String) :File {
            val path = folder + p.subSequence(0, p.lastIndexOf(".")) + ".json"
            val file = File(path)
            println(path)
            try {
                if(file.createNewFile()) {
                    println("File created!")
                } else {
                    println("File alredy exists")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return file
        }

    }
}