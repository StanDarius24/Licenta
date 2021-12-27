package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.StaticAssertionDeclaration
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTStaticAssertionDeclaration

object StaticAssertionDeclarationService {
    fun solveStaticAssertionDeclaration(staticAssertioncppast: CPPASTStaticAssertionDeclaration, statement: Statement?) {
        val staticAssertion = StaticAssertionDeclaration(null, null)
        val anonimStatement1 = AnonimStatement(null)
        ASTNodeService.solveASTNode(staticAssertioncppast.condition as ASTNode, anonimStatement1)
        val anonimStatement2 = AnonimStatement(null)
        ASTNodeService.solveASTNode(staticAssertioncppast.message as ASTNode, anonimStatement2)
        staticAssertion.addCondition(anonimStatement1)
        staticAssertion.addMessage(anonimStatement2)
        StatementMapper.addStatementToStatement(statement!!, staticAssertion)
    }
}