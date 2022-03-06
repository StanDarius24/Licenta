package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.LinkageSpecification
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTLinkageSpecification

object LinkageSpecificationService {
    fun solveLinkageSpecification(
        cppastLinkageSpecification: CPPASTLinkageSpecification,
        statement: Statement?
    ) {
        StatementMapper.addStatementToStatement(
            statement!!,
            LinkageSpecification(cppastLinkageSpecification.literal)
        )
    }
}
