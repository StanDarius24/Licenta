package com.stannis.function

import com.stannis.dataModel.statementTypes.*

object NameSpaceRegistry {

    val listOfNameSpace: ArrayList<NameSpace> by lazy { ArrayList() }


    fun checkInNameSpace(element: FunctionDefinition): Boolean {
        var nameSpaceToReplace: NameSpace? = null
        listOfNameSpace.forEach { nameSpace ->
            run {
                if (
                    nameSpace.name.equals(
                        ((element.declarator!![0].name as QualifiedName).qualifier?.get(0) as Name)
                            .name
                    )
                ) {
                    nameSpaceToReplace = nameSpace
                }
            }
        }
        if (nameSpaceToReplace != null) {
            return replaceFunctionWithImplementation(nameSpaceToReplace!!, element)
        }
        return false
    }

    private fun replaceFunctionWithImplementation(nameSpaceToReplace: NameSpace, element: FunctionDefinition): Boolean {
        var dataToRemove: SimpleDeclaration? = null
        nameSpaceToReplace.declarations!!.forEach { declaration -> run {
            if (declaration is SimpleDeclaration && declaration.declarators != null) {
                if (declaration.declarators!![0] is FunctionDeclarator) {
                    if ((declaration.declarators!![0] as FunctionDeclarator).name!! == (element.declarator!![0].name as QualifiedName).lastName) {
                        dataToRemove = declaration
                    }
                }
            } else if (declaration is SimpleDeclaration && declaration.declSpecifier is CompositeTypeSpecifier) {
                CompositeTypeRegistry.solveFunction(declaration.declSpecifier as CompositeTypeSpecifier, element)
            }
        }}
        if (dataToRemove != null) {
            nameSpaceToReplace.declarations!!.remove(dataToRemove!!)
            nameSpaceToReplace.declarations!!.add(element)
            return true
        }
        return false
    }
}

