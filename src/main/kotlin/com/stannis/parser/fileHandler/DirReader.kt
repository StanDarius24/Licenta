package com.stannis.parser.fileHandler

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class DirReader {

    companion object {
        //        val folder = "/home/stan/Desktop/Licenta/src/result/"
        const val folder = "C:\\Users\\Stannis\\Desktop\\KotlinLicenta\\src\\result\\"
        fun getAllFilesInResources(): ArrayList<String> {
            val list = ArrayList<String>()
            val projectDirAbsolutePath = Paths.get("").toAbsolutePath().toString()
            //            val resourcesPath = Paths.get(projectDirAbsolutePath,
            // "/src/main/resources/c++/rec")
            val resourcesPath =
                Paths.get(projectDirAbsolutePath, "src\\main\\resources\\c++\\rec\\test-C\\xtest")
            Files.walk(resourcesPath)
                .filter { item ->
                    Files.isRegularFile(item) &&
                            isCOrCppFileRelated(item.fileName.toString()) &&
                            !item.toString().contains("cmake-build-debug")
                }
                .forEach { item -> list.add(item.toString()) }
            list.sortDescending()
            return list
        }

        private fun isCOrCppFileRelated(text: String): Boolean {
            return text.endsWith(".cpp") ||
                text.endsWith(".cc") ||
                text.endsWith(".c") ||
                text.endsWith(".cxx") ||
                text.endsWith(".h") ||
                text.endsWith(".hh") ||
                text.endsWith(".hpp") ||
                text.endsWith(".inl") ||
                text.endsWith(".tcc")
        }

        fun makedir(path: String) {
            val pathNew = folder + path
            val dir = File(pathNew)
            if (!dir.exists()) {
                dir.mkdirs()
            }
        }

        fun createfile(p: String): File {
            val path = folder + p.subSequence(0, p.lastIndexOf(".")) + ".json"
            val file = File(path)
            println(path)
            try {
                if (file.createNewFile()) {
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