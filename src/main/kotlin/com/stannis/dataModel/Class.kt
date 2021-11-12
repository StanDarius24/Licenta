package com.stannis.dataModel

data class Class(
    var name: String?,
    var publicDecl: ArrayList<Declaration>?,
    var privateDecl: ArrayList<Declaration>?,
    var protectedDecl: ArrayList<Declaration>?,
    var publicMeth: ArrayList<Method>?,
    var privateMeth: ArrayList<Method?>?,
    var protectedMeth: ArrayList<Method?>?
    )
