package com.stannis.function

import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration

object DeclaratorParentExtractor {

    fun extractParentasFunctionCall(declaration: CPPASTSimpleDeclaration): CPPASTFunctionDefinition? {
        var parent = declaration.parent
        while(parent != null && parent.parent !is CPPASTFunctionDefinition) {
            parent = parent.parent
        }
        return parent as CPPASTFunctionDefinition?
    }
}