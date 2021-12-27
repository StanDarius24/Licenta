package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.QualifiedName
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTQualifiedName

object QualifiedNameService {
    fun solveQualifiedNameService(cppastQualifiedName: CPPASTQualifiedName, statement: Statement?) {
        val qualifiedName = QualifiedName(null, null)
        val anonimStatement1 = AnonimStatement(null)
        ASTNodeService.solveASTNode(cppastQualifiedName.lastName as ASTNode, anonimStatement1)
        qualifiedName.lastName = anonimStatement1.statement
        cppastQualifiedName.qualifier.iterator().forEachRemaining { qualifier -> run {
            val anonimStatement2 = AnonimStatement(null)
            ASTNodeService.solveASTNode(qualifier as ASTNode, anonimStatement2)
            qualifiedName.addQualifier(anonimStatement2.statement as Statement)
        }}
        StatementMapper.addStatementToStatement(statement!!, qualifiedName)
    }
}