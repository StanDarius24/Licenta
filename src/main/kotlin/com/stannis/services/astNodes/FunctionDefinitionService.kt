package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.FunctionDefinition
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition

object FunctionDefinitionService {
    fun solveFunctionDefinition(funcDef: CPPASTFunctionDefinition, statement: Statement?) {
        val functionDefinition = FunctionDefinition(null, null, null)
        val anonimStatement1 = AnonimStatement(null)
        if(funcDef.declSpecifier != null) {
            ASTNodeService.solveASTNode(funcDef.declSpecifier as ASTNode, anonimStatement1)
        }
        functionDefinition.declaratorSpecifier = anonimStatement1
        (funcDef.declarator as CPPASTFunctionDeclarator)
            .parameters.iterator().forEachRemaining { parameter -> run {
                val anonimStatement2 = AnonimStatement(null)
                ASTNodeService.solveASTNode(parameter as ASTNode, anonimStatement2)
                functionDefinition.addDeclarator(anonimStatement2)
            }}
        val anonimStatement3 = AnonimStatement(null)
        if(funcDef.body != null) {
            ASTNodeService.solveASTNode(funcDef.body as ASTNode, anonimStatement3)
        }
        functionDefinition.body = anonimStatement3
        StatementMapper.addStatementToStatement(statement!!, functionDefinition)
    }
}