package com.stannis.services.astNodes

import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTExpressionStatement

object ExpressionStatementService {

    fun solveExpressionStatement(
        data: CPPASTExpressionStatement,
        statement: com.stannis.dataModel.Statement?
    ) {
        ASTNodeService.solveASTNode(data.expression as ASTNode, statement)
    }
}
