package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.DeclWithParent
import com.stannis.dataModel.statementTypes.*
import com.stannis.services.astNodes.FunctionDefinitionService
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTDeclarationStatement
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition

object DeclarationRegistry {

    var listOfDeclaration: ArrayList<DeclWithParent> = ArrayList()

    var listOfInternDeclarations: ArrayList<DeclWithParent> = ArrayList()
    fun checkDeclaration(declarationStatement: DeclarationStatement, node: CPPASTDeclarationStatement) {
        val declarationWithParent = DeclWithParent(declaration = declarationStatement, parent = null)
        val parent = ParentExtractor.extractParent(node)
        if (parent is CPPASTFunctionDefinition) {
            declarationWithParent.parent = FunctionDefinitionService.setFunction(parent)
        } else {
            println()
        }
        listOfDeclaration.add(declarationWithParent)
        listOfInternDeclarations.add(declarationWithParent)
    }


}