package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.For
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object ForBlockService {

    fun solveForBlock(data: CPPASTForStatement, statement: Statement?) {
        val forT = For(null, null, null, null, null)
        StatementMapper.addStatementToStatement(
            statement!!, forT
        )
        solveInitialization(data, forT)
        solveConditionExpression(data, forT)
        solveIterationExpression(data, forT)
        solveBodyStatements(data, forT)
    }

    private fun solveBodyStatements(data: CPPASTForStatement, forT: For) {
        if(data.body is CPPASTCompoundStatement) {
            (data.body as CPPASTCompoundStatement).statements
                .iterator().forEachRemaining { statement ->
                    run {
                        val anonimStatement = AnonimStatement(null)
                        ASTNodeService.solveASTNode(statement as ASTNode, anonimStatement)
                        forT.addBody(anonimStatement.statement as Statement)
                    }
                }
        } else {
            val anonimStatement = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.body as ASTNode, anonimStatement)
            forT.addBody(anonimStatement.statement as Statement)
        }
    }

    private fun solveIterationExpression(data:CPPASTForStatement, forT: For) {
        if(data.iterationExpression != null) {
            val anonimStatement1 = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.iterationExpression as ASTNode, anonimStatement1)
            forT.addIteration(anonimStatement1.statement as Statement)
        }
        if(data.initializerStatement != null) {
            val anonimStatement2 = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.initializerStatement as ASTNode, anonimStatement2)
            forT.addInitializer(anonimStatement2.statement as Statement)
        }
    }

    private fun solveConditionExpression(data: CPPASTForStatement, forT: For) {
        val anonimStatement1 = AnonimStatement(null)
        if(data.conditionExpression != null) {
            ASTNodeService.solveASTNode(data.conditionExpression as ASTNode, anonimStatement1)
            forT.addConditionExpression(anonimStatement1.statement as Statement)
        }
        val anonimStatement2 = AnonimStatement(null)
        if(data.conditionExpression != null) {
            ASTNodeService.solveASTNode(data.conditionExpression as ASTNode, anonimStatement2)
            forT.addConditionExpression(anonimStatement2.statement as Statement)

        }
    }

    private fun solveInitialization(data: CPPASTForStatement, forT: For) {
        if (data.initializerStatement is CPPASTDeclarationStatement) {
            ((data.initializerStatement as CPPASTDeclarationStatement).declaration as CPPASTSimpleDeclaration)
                .declarators.iterator().forEachRemaining { declarator ->
                    run {
                        val anonimStatement1 = AnonimStatement(null)
                        ASTNodeService.solveASTNode(declarator as ASTNode, anonimStatement1)
                        forT.addInitializer(anonimStatement1.statement as Statement)
                    }
                }
        } else {
            val anonimStatement = AnonimStatement(null)
            ASTNodeService.solveASTNode(data.initializerStatement as ASTNode, anonimStatement)
            forT.addInitializer(anonimStatement.statement as Statement)
        }
    }
}