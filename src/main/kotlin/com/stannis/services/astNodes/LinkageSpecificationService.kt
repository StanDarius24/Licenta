package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.LinkageSpecification
import com.stannis.function.ExternDefinitionRegistry
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLinkageSpecification

object LinkageSpecificationService {
    fun solveLinkageSpecification(
        cppastLinkageSpecification: CPPASTLinkageSpecification,
        statement: Statement?
    ) {
        val linkageSpecification = LinkageSpecification(cppastLinkageSpecification.literal, ArrayList())
        cppastLinkageSpecification.declarations.iterator().forEachRemaining { decl -> run {
            val anonimStatement = AnonimStatement(null)
            ASTNodeService.solveASTNode(decl as ASTNode, anonimStatement)
            anonimStatement.statement?.let { linkageSpecification.declarations?.add(it) }
        } }
        ExternDefinitionRegistry.addToList(linkageSpecification)
        StatementMapper.addStatementToStatement(statement!!, linkageSpecification)
    }
}
