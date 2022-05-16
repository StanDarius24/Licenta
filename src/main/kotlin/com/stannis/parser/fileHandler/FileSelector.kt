package com.stannis.parser.fileHandler

object FileSelector {

    fun solvePath(s: String, filepath: String): String {
        val finalvalue = s.split(OperatingSystem.getSeparator()).toArray()
        if (filepath.contains("\\")) {
            filepath.split("\\").forEach { element ->
                run {
                    if (element == "..") {
                        finalvalue.removeAt(finalvalue.size - 1)
                    } else {
                        finalvalue.add(element)
                    }
                }
            }
            return finalvalue.joinToString(OperatingSystem.getSeparator())
        }
        if (finalvalue.size > 1) {
            filepath.split(OperatingSystem.getSeparator()).forEach { elem ->
                run { finalvalue.add(elem) }
            }
        } else {
            return finalvalue[0] + OperatingSystem.getSeparator() + filepath
        }
        return finalvalue.joinToString(OperatingSystem.getSeparator())
    }
}

private fun <E> List<E>.toArray(): ArrayList<String> {
    val data = ArrayList<String>()
    this.iterator().forEachRemaining { value -> run { data.add(value.toString()) } }
    return data
}
