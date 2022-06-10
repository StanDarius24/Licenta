package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.statementTypes.*
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier

object SimpleDeclarationRegistry {

    var globalDeclaration: ArrayList<DeclarationWithParent> = ArrayList()

    fun addToList(data: SimpleDeclaration, parent: ASTNode?) {
        if (parent !is CPPASTCompositeTypeSpecifier) {
            if (data.declarators != null && data.declarators!![0] is Declarator) {
               if (parent == null) {
                    if (
                        !globalDeclaration.contains(
                            DeclarationWithParent(declaration = data, parent = null)
                        )
                    ) {
                        globalDeclaration.add(
                            DeclarationWithParent(declaration = data, parent = null)
                        )
                    }
                }
            }
        } else if (data.declSpecifier is EnumerationSpecifier) {
            globalDeclaration.add(DeclarationWithParent(declaration = data, parent = null))
        }
    }

}
