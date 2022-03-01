package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.EqualsInitializer
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEqualsInitializer
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionCallExpression
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNewExpression

object EqualsInitializerService {
    fun solveEqualsInitializer(node: CPPASTEqualsInitializer, statement: Statement?) {
        val equals = EqualsInitializer(null, null)
        val anonimStatement = AnonimStatement(null)
        if(node.initializerClause is CPPASTFunctionCallExpression) {
            ASTNodeService.solveASTNode(
                (node.initializerClause as CPPASTFunctionCallExpression).functionNameExpression as ASTNode,
                anonimStatement
            )
            (node.initializerClause as CPPASTFunctionCallExpression).arguments.iterator().forEachRemaining { argument -> run {
                val anonimStatement2 = AnonimStatement(null)
                ASTNodeService.solveASTNode(argument as ASTNode, anonimStatement2)
                equals.addStatement(anonimStatement2.statement as Statement)
            } }
        } else if(node.initializerClause is CPPASTNewExpression) {
            ASTNodeService.solveASTNode(
                (node.initializerClause as CPPASTNewExpression) as ASTNode,
                anonimStatement
            )
        }
        equals.functionName = anonimStatement.statement
        StatementMapper.addStatementToStatement(statement!!, equals)
    }
}