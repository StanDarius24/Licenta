package com.stannis.function

import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

object ParentExtractor {

    fun extractParent(declaration: ASTNode): ASTNode? {
        var parent = declaration.parent
        while (parent != null &&
            parent !is CPPASTFunctionDefinition &&
            parent !is CPPASTCompositeTypeSpecifier &&
            parent !is CPPASTLinkageSpecification) {
            parent = parent.parent
        }
        return parent as ASTNode?
    }

    fun extractNameSpace(declaration: ASTNode): ASTNode? {
        var parent = declaration.parent
        while (parent != null &&
            parent !is CPPASTNamespaceDefinition) {
            parent = parent.parent
        }
        return parent as ASTNode?
    }

    fun checkParent(node: ASTNode): Boolean {
        return extractParent(node) != null
    }

}
