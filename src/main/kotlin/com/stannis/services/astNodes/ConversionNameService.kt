package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.ConversionName
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConversionName

object ConversionNameService {
    fun solveConversionName(cpastConversion: CPPASTConversionName, parent: Statement?) {
        val conv = ConversionName(null)
        val anonimStatement = AnonimStatement(null)
        ASTNodeService.solveASTNode(cpastConversion.typeId as ASTNode, anonimStatement)
        conv.typeId = anonimStatement.statement
        StatementMapper.addStatementToStatement(parent!!, conv)
    }
}