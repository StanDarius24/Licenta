package com.stannis.function

import com.stannis.dataModel.Statement
import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.Declarator
import com.stannis.dataModel.statementTypes.FunctionDefinition
import com.stannis.dataModel.statementTypes.SimpleDeclaration
import com.stannis.services.astNodes.FunctionDefinitionService
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition

object SimpleDeclarationRegistry {
    var globalDeclaration: ArrayList<DeclarationWithParent>? = null
    var internDeclaration: ArrayList<DeclarationWithParent>? = null

    fun addToList(data: SimpleDeclaration, parent: CPPASTFunctionDefinition?) {
        if(data.declarators != null &&  data.declarators!![0] is Declarator) {
            val declarationWithParent = DeclarationWithParent(data, null)
            if (parent != null) {
                val anonimStatement = AnonimStatement(null)
                solveFunctionDefinition(parent, anonimStatement)
                declarationWithParent.parent = anonimStatement.statement as FunctionDefinition
            }
            if (globalDeclaration == null) {
                globalDeclaration = ArrayList()
            }
            if (internDeclaration == null) {
                internDeclaration = ArrayList()
            }
            if(declarationWithParent.parent == null) {
                globalDeclaration!!.add(declarationWithParent)
                TranslationUnitRegistry.finalTranslation.globalDeclaration = globalDeclaration
            } else {
                internDeclaration!!.add(declarationWithParent)
                TranslationUnitRegistry.finalTranslation.internDeclaration = internDeclaration
            }
        }
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