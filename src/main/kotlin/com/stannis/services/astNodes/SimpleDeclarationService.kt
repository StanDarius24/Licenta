package com.stannis.services.astNodes

import com.stannis.dataModel.*
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.SimpleDeclaration
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object SimpleDeclarationService {

    fun solveDeclSpecifier(declaration: CPPASTSimpleDeclaration, statement: Statement?): Boolean {

        val simpleDeclaration = SimpleDeclaration(null, null)
        declaration.declarators.iterator().forEachRemaining { decl -> run {
            val anonimStatement = AnonimStatement(null)
            ASTNodeService.solveASTNode(decl as ASTNode, anonimStatement)
            simpleDeclaration.addDeclarators(anonimStatement.statement as Statement)
        } }
        val anonimStatement2 = AnonimStatement(null)
        ASTNodeService.solveASTNode(declaration.declSpecifier as ASTNode, anonimStatement2)
        simpleDeclaration.declSpecifier = anonimStatement2.statement
        StatementMapper.addStatementToStatement(statement!!, simpleDeclaration)
        return true
    }


}