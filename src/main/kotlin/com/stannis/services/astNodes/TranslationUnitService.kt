package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.TranslationUnit
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTranslationUnit

object TranslationUnitService {

    lateinit var translationUnitCache: TranslationUnit

    fun solveTranslationUnit(node: CPPASTTranslationUnit) {
        val translationUnit = TranslationUnit(include = null, listOfDeclarations = null)
        if (node.allPreprocessorStatements.isNotEmpty()) {
            translationUnit.include = ArrayList()
            node.allPreprocessorStatements.forEach { directive -> run {
                (translationUnit.include as ArrayList<String>).add(directive.rawSignature)
            } }
        }
        if (node.declarations.isNotEmpty()) {
            translationUnit.listOfDeclarations = ArrayList()
            node.declarations.forEach { decl -> run {
                val anonimStatement = AnonimStatement.getNewAnonimStatement()
                ASTNodeService.solveASTNode(decl as ASTNode, anonimStatement)
                translationUnit.listOfDeclarations!!.add(anonimStatement.statement as Statement)
                ASTNodeService.modifier = "public"
            } }
        }
        translationUnitCache = translationUnit
    }
}
