package com.stannis.function

import com.stannis.dataModel.Statement
import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.statementTypes.*
import com.stannis.services.astNodes.FunctionDefinitionService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTranslationUnit

object SimpleDeclarationRegistry {

    var globalDeclaration: ArrayList<DeclarationWithParent>? = null
    var internDeclaration: ArrayList<DeclarationWithParent>? = null

    fun addToList(data: SimpleDeclaration, parent: ASTNode?) {
        if (parent !is CPPASTCompositeTypeSpecifier) {
            if (data.declarators != null && data.declarators!![0] is Declarator) {
                val declarationWithParent = DeclarationWithParent(declaration = data, parent = null)
                if (parent != null) {
                    val anonimStatement = AnonimStatement.getNewAnonimStatement()
                    if (parent is CPPASTFunctionDefinition) {
                        solveFunctionDefinition(parent, anonimStatement)
                        declarationWithParent.parent =
                            anonimStatement.statement as FunctionDefinition
                        if (!checkIfClassStructure(parent)) {
                            if (globalDeclaration == null) {
                                globalDeclaration = ArrayList()
                            }
                            if (internDeclaration == null) {
                                internDeclaration = ArrayList()
                            }
                            if (declarationWithParent.parent == null) {
                                if (!globalDeclaration!!.contains(declarationWithParent)) {
                                    globalDeclaration!!.add(declarationWithParent)
                                }
                            } else {
                                if (!internDeclaration!!.contains(declarationWithParent)) {
                                    internDeclaration!!.add(declarationWithParent)
                                }
                            }
                        }
                    }
                }
            } else if (data.declSpecifier is EnumerationSpecifier) {
                if (globalDeclaration == null) {
                    globalDeclaration = ArrayList()
                }
                globalDeclaration!!.add(DeclarationWithParent(declaration = data, parent = null))
            }
        }
    }

    private fun checkIfClassStructure(parent: CPPASTFunctionDefinition): Boolean {
        var newparent = parent.parent
        while (newparent != null &&
            !(newparent is CPPASTCompositeTypeSpecifier) &&
            !(newparent is CPPASTTranslationUnit)) {
            newparent = newparent.parent
        }
        return newparent is CPPASTCompositeTypeSpecifier
    }

    fun clearList() {
        globalDeclaration = null
        internDeclaration = null
    }

    private fun solveFunctionDefinition(funcDef: CPPASTFunctionDefinition, statement: Statement?) {
        val functionDefinition = FunctionDefinitionService.setFunction(funcDef)
        StatementMapper.addStatementToStatement(statement!!, functionDefinition)
    }
}
