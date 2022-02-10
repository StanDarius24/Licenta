package com.stannis.function

import com.stannis.dataModel.Statement
import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.Declarator
import com.stannis.dataModel.statementTypes.FunctionDefinition
import com.stannis.dataModel.statementTypes.SimpleDeclaration
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition

object SimpleDeclarationRegistry {
    var list: ArrayList<DeclarationWithParent>? = null

    fun addToList(data: SimpleDeclaration, parent: CPPASTFunctionDefinition?) {
        if(data.declarators != null &&  data.declarators!![0] is Declarator) {
            val declarationWithParent = DeclarationWithParent(data, null)
            if (parent != null) {
                val anonimStatement = AnonimStatement(null)
                solveFunctionDefinition(parent, anonimStatement)
                declarationWithParent.parent = anonimStatement.statement as FunctionDefinition
            }
            if (list == null) {
                list = ArrayList()
            }
            list!!.add(declarationWithParent)
            println(list)
        }
    }

    fun clearList() {
        list = null
    }

    private fun solveFunctionDefinition(funcDef: CPPASTFunctionDefinition, statement: Statement?) {
        val functionDefinition = FunctionDefinition(null, null, null)   // only functions with implementation
        val anonimStatement1 = AnonimStatement(null)
        if(funcDef.declSpecifier != null) {
            ASTNodeService.solveASTNode(funcDef.declSpecifier as ASTNode, anonimStatement1)
        }
        functionDefinition.declaratorSpecifier = anonimStatement1.statement
        val anonimStatement2 = AnonimStatement(null)
        if(funcDef.declarator != null) {
            ASTNodeService.solveASTNode(funcDef.declarator as ASTNode, anonimStatement2)
        }
        functionDefinition.addDeclarator(anonimStatement2.statement as Statement)
        StatementMapper.addStatementToStatement(statement!!, functionDefinition)
    }
}