package com.stannis.parser.fileHandler

object FileSelector {

    private lateinit var listOfPaths: ArrayList<String>

    private lateinit var listOfNames: ArrayList<String>

    private var nameSet: String? = null

    fun setListOfPathsParam(list: ArrayList<String>) {
        this.listOfPaths = list
        this.listOfNames = list.map { data ->
            run {
                data.split("\\").last()
            }
        }.toArray()
    }

    fun getHeaderClassFirst(): String {
        if (listOfNames.isEmpty())
        {
            return ""
        }
        val name = listOfNames.first()
        nameSet = name.split(".")[0] + ".cpp"
        val data = sendCorespondingFileName(name, false)
        listOfNames.remove(name)
        return data
    }

    fun getCppFile(): String {
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

}

private fun <E> List<E>.toArray(): ArrayList<String> {
    val data = ArrayList<String>()
    this.iterator().forEachRemaining { value -> run {
        data.add(value.toString())
    } }
    return data
}

