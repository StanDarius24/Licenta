package com.stannis.declSpecifier

import com.stannis.dataModel.*
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object SimpleDeclarationService {

    fun solveDeclSpecifier(declaration: CPPASTSimpleDeclaration, statement: Statement): Boolean {
        ASTNodeService.solveASTNode(declaration.declSpecifier as ASTNode, statement)
        return true
    }


}