package com.stannis.parser.fileHandler

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object DirReader {

        lateinit var folder: String
        lateinit var newFolderPath: String

        fun getAllFilesInResources(finalPath: String, test: (input: Path) -> Boolean): ArrayList<String> {
            folder = finalPath
            OperatingSystem.getOPSystem()
            return getFilesFromDir(finalPath, test)
        }

        fun getFilesFromDir(finalPath: String, test: (input: Path) -> Boolean) :ArrayList<String> {
            val list = ArrayList<String>()
            val resourcesPath = Paths.get(finalPath)
            Files.walk(resourcesPath)
                .filter { item ->
                    test(item)
                }
                .forEach { item -> list.add(item.toString()) }
            return list
        }

        fun isCOrCppFileRelated(text: String): Boolean {
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

        private fun isHeaderFile(text: String): Boolean {
            return text.endsWith(".h") ||
                    text.endsWith(".hh") ||
                    text.endsWith(".hpp")
        }

        fun makedir(path: String): String {
            var data = folder.split(OperatingSystem.getSeparator())
            val last = data.last()
            data = data.dropLast(1)
            val x = data.joinToString(OperatingSystem.getSeparator()) + OperatingSystem.getSeparator() + "result" + OperatingSystem.getSeparator() + last
            newFolderPath = x
            val pathNew = x + path
            val dir = File(pathNew.split(OperatingSystem.getSeparator()).dropLast(1).joinToString(OperatingSystem.getSeparator()))
            if (!dir.exists()) {
                dir.mkdirs()
            }
            return x
        }

        fun createfile(p: String): File {
            val file = File(newFolderPath + OperatingSystem.getSeparator() + p)
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

    fun getHeadersFilesFromProject(absolutPath: String): ArrayList<String> {
        folder = absolutPath
        OperatingSystem.getOPSystem()
        val list = ArrayList<String>()
        val data = (absolutPath.split(OperatingSystem.getSeparator()) as java.util.ArrayList<*>)
        data.remove(data.last())
        data.joinToString(OperatingSystem.getSeparator()) + OperatingSystem.getSeparator() +"result"
        val resourcesPath = Paths.get(absolutPath)
        Files.walk(resourcesPath)
            .filter { item ->
                Files.isRegularFile(item) &&
                        isHeaderFile(item.fileName.toString()) &&
                        !item.toString().contains("cmake-build-debug")
            }
            .forEach { item -> list.add(item.toString()) }
        return list
    }
}