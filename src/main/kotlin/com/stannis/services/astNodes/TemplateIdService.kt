package com.stannis.services.astNodes

import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.TemplateId
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateId

object TemplateIdService {
    fun solveTemplateId(templateIdcpp: CPPASTTemplateId, statement: Statement?) {
        val templateData = TemplateId(templateName = null, templateArguments = null)
        val anonimStatement = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(templateIdcpp.templateName as ASTNode, anonimStatement)
        templateData.templateName = anonimStatement.statement as NameInterface
        templateIdcpp.templateArguments.iterator().forEachRemaining { template -> run {
            val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
            ASTNodeService.solveASTNode(template as ASTNode, anonimStatement1)
            templateData.addStatement(anonimStatement1.statement as Statement)
        } }
        StatementMapper.addStatementToStatement(statement!!, templateData)
    }
}