package com.stannis.parser.fileHandler

object FileSelector {

    private lateinit var listOfPaths: ArrayList<String>

    private lateinit var listOfNames: ArrayList<String>

    private var nameSet: String? = null

    fun setListOfPathsParam(list: ArrayList<String>) {
        this.listOfPaths = list
        this.listOfNames = list.map { data ->
            run {
                data.split(OperatingSystem.getOPSystem()).last()
            }
        }.toArray()
    }

    fun getHeaderClassFirst(): String {
        if (listOfNames.isEmpty())
        {
            return ""
        }
        val name = listOfNames.first()
        val datax = name.split(".")
        nameSet = datax.get(datax.lastIndex - 1) + ".cpp"
        val data = sendCorespondingFileName(name, false)
        listOfNames.remove(name)
        checkIfCppExists(name)
        return data
    }

    private fun checkIfCppExists(path: String) {
        val x = path.split(OperatingSystem.getSeparator())
        val ras = x.get(x.size -1).split(".")[0]
        nameSet = listOfNames.find { pathName -> pathName.contains(ras) }
    }

    fun getCppFile(): String { // C:\Users\Stannis\Desktop\KotlinLicenta\src\main\resources\c++\rec\test-C\Test.cpp
        val data = sendCorespondingFileName(nameSet!!, true)
        listOfNames.remove(nameSet)
        return data
    }

    private fun sendCorespondingFileName(name: String, type: Boolean): String {
        return if (type) {
            val x = listOfPaths.find { requestedString -> requestedString.contains(name) }.toString()
            listOfPaths.remove(x)
            x
        } else {
            val x = listOfPaths.find { requestedString -> requestedString.contains(name) }.toString()
            listOfPaths.remove(x)
            x
        }
    }

    fun solvePath(s: String, filepath: String): String {
        val finalvalue = s.split("/")
        if(filepath.contains("\\")) {
            filepath.split("\\").forEach { element -> run {
                if(element == "..") {
                    (finalvalue as ArrayList).removeAt(finalvalue.size-1)
                } else {
                    (finalvalue as ArrayList).add(element)
                }
            } }
            return finalvalue.joinToString(OperatingSystem.getSeparator())
        }
        filepath.split(OperatingSystem.getSeparator()).forEach { elem -> run {
            (finalvalue as ArrayList).add(elem)
        } }
        return finalvalue.joinToString(OperatingSystem.getSeparator())
    }

}

private fun <E> List<E>.toArray(): ArrayList<String> {
    val data = ArrayList<String>()
    this.iterator().forEachRemaining { value -> run {
        data.add(value.toString())
    } }
    return data
}

