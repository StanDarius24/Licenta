package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier
import com.stannis.function.CompositeTypeRegistry
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier

object CompositeTypeSpecifierService {
    fun solveCompositeTypeSpecifier(
        cppastCompositeTypeSpecifier: CPPASTCompositeTypeSpecifier,
        statement: Statement?
    ) {
        val data = setClassDefinitions(cppastCompositeTypeSpecifier)
        cppastCompositeTypeSpecifier.members.iterator().forEachRemaining { declaration ->
            run {
                val anonimStatement = AnonimStatement(null)
                ASTNodeService.solveASTNode(declaration as ASTNode, anonimStatement)
                if (anonimStatement.statement != null) {
                    data.addStatement(anonimStatement.statement as Statement)
                }
            }
        }
        CompositeTypeRegistry.addCompositeTypeSpecifier(cppastCompositeTypeSpecifier, data)
        StatementMapper.addStatementToStatement(statement!!, data)
    }

    fun setClassDefinitions(
        cppastCompositeTypeSpecifier: CPPASTCompositeTypeSpecifier
    ): CompositeTypeSpecifier {
        val data = CompositeTypeSpecifier(null, null, null, cppastCompositeTypeSpecifier.key)
        val anonimStatement1 = AnonimStatement(null)
        ASTNodeService.solveASTNode(cppastCompositeTypeSpecifier.name as ASTNode, anonimStatement1)
        data.name = anonimStatement1.statement
        cppastCompositeTypeSpecifier.baseSpecifiers.iterator().forEachRemaining { base ->
            run {
                val anonimStatement2 = AnonimStatement(null)
                ASTNodeService.solveASTNode(base as ASTNode, anonimStatement2)
                data.addBase(anonimStatement2.statement!!)
            }
        }
        return data
    }
}
