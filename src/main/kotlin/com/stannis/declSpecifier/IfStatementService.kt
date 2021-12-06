package com.stannis.declSpecifier

import com.stannis.dataModel.Method
import com.stannis.dataModel.statementTypes.If
import com.stannis.services.CoreParserClass
import com.stannis.services.MethodService
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class IfStatementService {
    fun solveIfStatement(data: CPPASTIfStatement,  method: Method?) {
        println("ifStatement")
        val ifT = If(null, null, null, null, null)
        MethodService.getInstance().addStatement(method!!, ifT)
        if (data.conditionExpression != null) {
            ASTNodeService.getInstance()
                .solveASTNode(
                    data.conditionExpression as ASTNode,
                    ifT
                )
        }
        val ifBlock = MethodService.getInstance().createMethod()
        ifT.addIfBlock(ifBlock)
        val elseBlock = MethodService.getInstance().createMethod()
        ifT.addElseBlock(elseBlock)
        if (data.thenClause != null)
            CoreParserClass.seeCPASTCompoundStatement(data.thenClause, ifBlock)
        if (data.elseClause != null)
            CoreParserClass.seeCPASTCompoundStatement(data.elseClause, elseBlock)
    }
}