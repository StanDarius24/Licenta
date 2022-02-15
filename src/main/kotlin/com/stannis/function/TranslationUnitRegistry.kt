package com.stannis.function

import com.stannis.dataModel.Statement
import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.complexStatementTypes.FinalTranslation
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.Declarator
import com.stannis.dataModel.statementTypes.FunctionDefinition
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTranslationUnit

object TranslationUnitRegistry {
    var finalTranslation = FinalTranslation(null, null, null)

    fun storeTranslationUnit(translationUnit: CPPASTTranslationUnit) {
        translationUnit.includeDirectives.iterator().forEachRemaining { directive -> run {
            val anonimStatement = AnonimStatement(null)
            ASTNodeService.solveASTNode(directive as ASTNode, anonimStatement)
            finalTranslation.addDirectives(anonimStatement.statement as Statement)
        } }
    }

    fun setFunctionCalls(data :ArrayList<FunctionDefinition>?) {
        finalTranslation.functionCalls = data
    }

    fun setDeclaration(decl: ArrayList<DeclarationWithParent>) {
        finalTranslation.declaration = decl
    }
}