package com.stannis.function

import com.stannis.dataModel.statementTypes.FunctionDeclarator
import com.stannis.dataModel.statementTypes.Name
import com.stannis.dataModel.statementTypes.QualifiedName
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLinkageSpecification

object FunctionDeclaratorRegistry {

    var list: ArrayList<FunctionDeclarator>? = null


    var sw: Boolean = false

    fun addToList(
        functionDeclarator: FunctionDeclarator,
        node: CPPASTFunctionDeclarator
    ) { // declarari de functii, fara body. metode fara implementare, interfete etc.
        if (ParentExtractor.extractParentForFunctionDeclarator(node)
                !is CPPASTCompositeTypeSpecifier &&
                ParentExtractor.extractParentForFunctionDeclarator(node)
                    !is CPPASTLinkageSpecification
        ) {
            if (list == null) { // here
                list = ArrayList()
            } else {
                if (functionDeclarator.name is Name) {
                    sw = solveName(functionDeclarator)
                } else if (functionDeclarator.name is QualifiedName) {
                    sw = solveQualifiedName(functionDeclarator)
                }
            }
            if (!sw) {
                if (!list!!.contains(functionDeclarator)) {
                    list!!.add(functionDeclarator)
                }
            }
        }
    }

    private fun solveQualifiedName(functionDeclarator: FunctionDeclarator): Boolean {
        var sw1 = false
        list!!.iterator().forEachRemaining { element ->
            run {
                if (element.name is QualifiedName) {
                    if ((element.name as QualifiedName) == functionDeclarator.name) {
                        sw1 = checkParameters(element, functionDeclarator)
                    }
                } else if (element.name is Name) {
                    if ((functionDeclarator.name as QualifiedName).lastName!! == element.name) {
                        sw1 = true
                    }
                }
            }
        }
        return sw1
    }

    private fun checkParameters(
        elementFromList: FunctionDeclarator,
        functionDeclarator: FunctionDeclarator
    ): Boolean {
        var number = 0
        if (elementFromList.parameter != null) {
            elementFromList.parameter!!.iterator().forEachRemaining { element ->
                run {
                    if (functionDeclarator.parameter != null) {
                        functionDeclarator.parameter!!.iterator().forEachRemaining { parameter ->
                            run {
                                if (element == parameter) {
                                    number += 1
                                }
                            }
                        }
                    }
                }
            }
            if (functionDeclarator.parameter != null) {
                if (number == functionDeclarator.parameter!!.size) {
                    return true
                }
            }
        }
        return false
    }

    private fun solveName(functionDeclarator: FunctionDeclarator): Boolean {
        var sw1 = false
        list!!.iterator().forEachRemaining { element ->
            run {
                if (element.name is Name) {
                    if ((element.name as Name) == functionDeclarator.name) {
                        sw1 = sw1 || checkParameters(element, functionDeclarator)
                    }
                }
            }
        }
        return sw1
    }
}
