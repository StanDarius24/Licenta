package com.stannis.declSpecifier

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.Return
import com.stannis.services.astNodes.ConditionalExpressionService
import com.stannis.services.FunctionCallsService
import com.stannis.services.MethodService
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ReturnStatementService {

    fun solveReturnStatement(data: CPPASTReturnStatement, method: Method?) {
        val returnT = Return(null, null, null)
        if(data.returnValue != null) {
            ASTNodeService.getInstance()
                .solveASTNode(data.returnValue as ASTNode,
                returnT)
        }
        StatementMapper.addStatementToStatement(method as Statement, returnT)
    }
}