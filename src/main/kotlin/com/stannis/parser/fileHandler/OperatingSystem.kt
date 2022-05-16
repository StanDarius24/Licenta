package com.stannis.parser.fileHandler


object OperatingSystem {

    lateinit var operatingType: String

    fun getOPSystem(): String {
        val name = System.getProperty("os.name")
        if(name.contains("Windows")) {
            operatingType = "Windows"
        } else if (name.contains("Linux")){
            operatingType = "Linux"
        } else if (name.contains("Mac")) {
            operatingType = "Mac"
        }
        return operatingType
    }

    fun getSeparator(): String{
        if(!this::operatingType.isInitialized) {
            getOPSystem()
        }
        if (operatingType == "Windows") {
            return "\\"
        } else if (operatingType == "Linux") {
            return "/"
        } else if (operatingType == "Mac") {
            return "?"
        }
        return "Not supported"
    }
}