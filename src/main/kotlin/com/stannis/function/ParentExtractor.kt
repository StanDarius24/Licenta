package com.stannis.function

import org.eclipse.cdt.core.dom.ast.IASTNode
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration

object ParentExtractor {

    fun extractParentasFunctionCall(declaration: CPPASTSimpleDeclaration): ASTNode? {
        var parent = declaration.parent
        while (parent != null &&
            parent !is CPPASTFunctionDefinition &&
            parent !is CPPASTCompositeTypeSpecifier
        ) {
            parent = parent.parent
        }
        return parent as ASTNode?
    }

    fun extractParentForFunctionDeclarator(node: CPPASTFunctionDeclarator): IASTNode? {
        var parent = node.parent
        while (parent != null && parent !is CPPASTCompositeTypeSpecifier) {
            parent = parent.parent
        }
        return parent
    }
}
