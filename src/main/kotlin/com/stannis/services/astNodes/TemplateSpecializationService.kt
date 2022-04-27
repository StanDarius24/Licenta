package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.TemplateSpecialization
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateSpecialization

object TemplateSpecializationService {
    fun solveTemplateSpecialization(
        templateSolver: CPPASTTemplateSpecialization,
        statement: Statement?
    ) {
        val templSpec = TemplateSpecialization(declaration = null)
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(templateSolver.declaration as ASTNode, anonimStatement)
        templSpec.declaration = anonimStatement.statement
        StatementMapper.addStatementToStatement(statement!!, templSpec)
    }
}
