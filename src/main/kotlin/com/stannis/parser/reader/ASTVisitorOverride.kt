package com.stannis.parser.reader

import org.eclipse.cdt.core.dom.ast.ASTVisitor
import org.eclipse.cdt.core.dom.ast.IASTDeclaration

class ASTVisitorOverride: ASTVisitor() {
    override fun visit(declaration: IASTDeclaration): Int {
        println("Found a declaration: " + declaration.rawSignature)
        return PROCESS_CONTINUE
    }
}