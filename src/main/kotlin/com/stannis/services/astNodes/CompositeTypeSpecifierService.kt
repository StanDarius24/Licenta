package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier

object CompositeTypeSpecifierService {
    fun solveCompositeTypeSpecifier(cppastCompositeTypeSpecifier: CPPASTCompositeTypeSpecifier, statement: Statement?) {
        val data = CompositeTypeSpecifier(cppastCompositeTypeSpecifier.rawSignature, null)
        cppastCompositeTypeSpecifier.members.iterator().forEachRemaining { declaration ->
            run {
                val anonimStatement = AnonimStatement(null)
                ASTNodeService.solveASTNode(declaration as ASTNode, anonimStatement)
                if(anonimStatement.statement != null) {
                    data.addStatement(anonimStatement.statement as Statement)
                }
            }
        }
        StatementMapper.addStatementToStatement(statement!!, data)
    }
}