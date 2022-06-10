package com.stannis.function

import com.stannis.dataModel.statementTypes.FunctionDeclarator
import com.stannis.dataModel.statementTypes.Name
import com.stannis.dataModel.statementTypes.QualifiedName
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLinkageSpecification
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamespaceDefinition

object FunctionDeclaratorRegistry {

    var list: ArrayList<FunctionDeclarator> = ArrayList()

    var sw: Boolean = false

    fun addToList(
        functionDeclarator: FunctionDeclarator,
        node: CPPASTFunctionDeclarator
    ) {
        val parentPrinc = ParentExtractor.extractParent(node)
        if (parentPrinc !is CPPASTCompositeTypeSpecifier &&
                parentPrinc !is CPPASTLinkageSpecification &&
                    parentPrinc !is CPPASTNamespaceDefinition
        ) {

            if (functionDeclarator.name is Name) {
                sw = solveName(functionDeclarator)
            } else if (functionDeclarator.name is QualifiedName) {
                sw = solveQualifiedName(functionDeclarator)
            }
            if (!sw) {
                if (!list.contains(functionDeclarator)) {
                    if (!ParentExtractor.checkParent(node)) {
                        list.add(functionDeclarator)
                    }
                }
            }
        }
    }

    private fun solveQualifiedName(functionDeclarator: FunctionDeclarator): Boolean {
        var sw1 = false
        list.iterator().forEachRemaining { element ->
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
        list.iterator().forEachRemaining { element ->
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
