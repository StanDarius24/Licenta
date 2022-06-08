package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import org.eclipse.cdt.internal.core.dom.parser.ASTNode

interface ASTNodeSolverInterface {
    fun solveASTNode(node: ASTNode, statement: Statement?)
}