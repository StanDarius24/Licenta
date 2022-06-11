package com.stannis.services.astNodes

import com.stannis.dataModel.DeclarationSpecifierParent
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.NamedTypeSpecifier
import com.stannis.dataModel.statementTypes.SimpleDeclSpecifier
import com.stannis.dataModel.statementTypes.SimpleDeclaration
import com.stannis.function.ParentExtractor
import com.stannis.function.SimpleDeclarationRegistry
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration

object SimpleDeclarationService {

    fun solveDeclSpecifier(declaration: CPPASTSimpleDeclaration, statement: Statement?) {
        val simpleDeclaration = SimpleDeclaration(declarators = null, declSpecifier = null)
        declaration.declarators.iterator().forEachRemaining { decl ->
            run {
                val anonimStatement = AnonimStatement.getNewAnonimStatement()
                ASTNodeService.solveASTNode(decl as ASTNode, anonimStatement)
                simpleDeclaration.addDeclarators(anonimStatement.statement as Statement)
            }
        }
        val anonimStatement2 = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(declaration.declSpecifier as ASTNode, anonimStatement2)
        simpleDeclaration.declSpecifier = anonimStatement2.statement as DeclarationSpecifierParent
        StatementMapper.addStatementToStatement(statement!!, simpleDeclaration)
        if (anonimStatement2.statement is SimpleDeclSpecifier || anonimStatement2.statement is NamedTypeSpecifier) {
            SimpleDeclarationRegistry.addToList(
                simpleDeclaration,
                ParentExtractor.extractParent(declaration),
            )
        }
    }
}
