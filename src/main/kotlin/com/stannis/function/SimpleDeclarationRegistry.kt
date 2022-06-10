package com.stannis.function

import com.stannis.dataModel.Statement
import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.statementTypes.*
import com.stannis.services.astNodes.FunctionDefinitionService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition

object SimpleDeclarationRegistry {

    val globalDeclaration: ArrayList<DeclarationWithParent> by lazy { ArrayList() }
    val internDeclaration: ArrayList<DeclarationWithParent> by lazy { ArrayList() }

    fun addToList(data: SimpleDeclaration, parent: ASTNode?) {
        val declarationWithParent = DeclarationWithParent(declaration = data, parent = null)
        if (parent !is CPPASTCompositeTypeSpecifier) {
            if (data.declarators != null && data.declarators!![0] is Declarator) {
                if (parent != null) {
                    val anonimStatement = AnonimStatement.getNewAnonimStatement()
                    if (parent is CPPASTFunctionDefinition) {
                        solveFunctionDefinition(parent, anonimStatement)
                        declarationWithParent.parent =
                            anonimStatement.statement as FunctionDefinition
                        if (!internDeclaration.contains(declarationWithParent)) {
                            internDeclaration.add(declarationWithParent)
                        }
                    }
                } else {
                    if (
                        !globalDeclaration.contains(
                            DeclarationWithParent(declaration = data, parent = null)
                        )
                    ) {
                        globalDeclaration.add(
                            DeclarationWithParent(declaration = data, parent = null)
                        )
                    }
                }
            }
        } else if (data.declSpecifier is EnumerationSpecifier) {
            globalDeclaration.add(DeclarationWithParent(declaration = data, parent = null))
        }
    }

    private fun solveFunctionDefinition(funcDef: CPPASTFunctionDefinition, statement: Statement?) {
        val functionDefinition = FunctionDefinitionService.setFunction(funcDef)
        StatementMapper.addStatementToStatement(statement!!, functionDefinition)
    }
}
