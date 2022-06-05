package com.stannis.services.astNodes

import com.stannis.dataModel.DeclarationSpecifierParent
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.FunctionDefinition
import com.stannis.function.FunctionDefinitionRegistry
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition

object FunctionDefinitionService {
    fun solveFunctionDefinition(funcDef: CPPASTFunctionDefinition, statement: Statement?) {
        val functionDefinition = setFunction(funcDef)
        val anonimStatement3 = AnonimStatement.getNewAnonimStatement()
        if (funcDef.body != null) {
            ASTNodeService.solveASTNode(funcDef.body as ASTNode, anonimStatement3)
        }
        functionDefinition.addToBody(anonimStatement3.statement as Statement)
        FunctionDefinitionRegistry.addToList(functionDefinition)
        StatementMapper.addStatementToStatement(statement!!, functionDefinition)
    }

    fun setFunction(funcDef: CPPASTFunctionDefinition): FunctionDefinition {
        val functionDefinition =
            FunctionDefinition(declaratorSpecifier = null, declarator = null, body = null, cyclomaticComplexity = 1, modifier = ASTNodeService.modifier) // only functions with implementation
        val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
        if (funcDef.declSpecifier != null) {
            ASTNodeService.solveASTNode(funcDef.declSpecifier as ASTNode, anonimStatement1)
        }
        functionDefinition.declaratorSpecifier = anonimStatement1.statement as DeclarationSpecifierParent
        val anonimStatement2 = AnonimStatement.getNewAnonimStatement()
        if (funcDef.declarator != null) {
            ASTNodeService.solveASTNode(funcDef.declarator as ASTNode, anonimStatement2)
        }
        functionDefinition.addDeclarator(anonimStatement2.statement as Statement)
        return functionDefinition
    }
}
