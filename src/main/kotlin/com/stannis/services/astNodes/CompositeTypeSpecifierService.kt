package com.stannis.services.astNodes

import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.Name
import com.stannis.dataModel.statementTypes.TemplateId
import com.stannis.function.CompositeTypeRegistry
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration

object CompositeTypeSpecifierService {
    fun solveCompositeTypeSpecifier(
        cppastCompositeTypeSpecifier: CPPASTCompositeTypeSpecifier,
        statement: Statement?
    ) {
        val data = setClassDefinitions(cppastCompositeTypeSpecifier)
        cppastCompositeTypeSpecifier.members.iterator().forEachRemaining { declaration ->
            run {
                val anonimStatement = AnonimStatement.getNewAnonimStatement()
                ASTNodeService.solveASTNode(declaration as ASTNode, anonimStatement)
                if (anonimStatement.statement != null) {
                    data.addStatement(anonimStatement.statement as Statement)
                }
            }
        }
        CompositeTypeRegistry.addCompositeTypeSpecifier(cppastCompositeTypeSpecifier, data)
        StatementMapper.addStatementToStatement(statement!!, data)
    }

    private fun setClassDefinitions(
        cppastCompositeTypeSpecifier: CPPASTCompositeTypeSpecifier
    ): CompositeTypeSpecifier {
        val data =
            CompositeTypeSpecifier(
                name = null,
                baseSpec = null,
                declarations = null,
                key = cppastCompositeTypeSpecifier.key
            )
        val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(cppastCompositeTypeSpecifier.name as ASTNode, anonimStatement1)
        if (anonimStatement1.statement is Name) {
            if ((anonimStatement1.statement as Name).name.toString() != "") {
                data.name = anonimStatement1.statement as NameInterface
            } else {
                if ((cppastCompositeTypeSpecifier.parent as CPPASTSimpleDeclaration).declarators.isNotEmpty()) {
                    ASTNodeService.solveASTNode((cppastCompositeTypeSpecifier.parent as CPPASTSimpleDeclaration).declarators[0].name as ASTNode, anonimStatement1)
                    data.name = anonimStatement1.statement as NameInterface
                }
            }
        } else if (anonimStatement1.statement is TemplateId) {
            data.name = (anonimStatement1.statement as TemplateId).templateName as NameInterface
        }
        cppastCompositeTypeSpecifier.baseSpecifiers.iterator().forEachRemaining { base ->
            run {
                val anonimStatement2 = AnonimStatement.getNewAnonimStatement()
                ASTNodeService.solveASTNode(base as ASTNode, anonimStatement2)
                data.addBase(anonimStatement2.statement!!)
            }
        }
        return data
    }
}
