package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.AnonimStatement
import com.stannis.dataModel.statementTypes.TemplateDeclaration
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTemplateDeclaration

object TemplateDeclarationService {

    fun solveTemplateDeclaration(templateDecl: CPPASTTemplateDeclaration, statement: Statement?) {
        val template = TemplateDeclaration(declaration = null,parameters = null, templateScope = null)
        val anonimDecl = AnonimStatement.getNewAnonimStatement()
        ASTNodeService.solveASTNode(templateDecl.declaration as ASTNode, anonimDecl)
        template.addDeclaration(anonimDecl)
        val templateScope = AnonimStatement.getNewAnonimStatement()
        //        ASTNodeService.getInstance() CPPTemplateScope it s not ASTNODE
        //            .solveASTNode(templateDecl.scope as ASTNode, templateScope)
        templateDecl.templateParameters.iterator().forEachRemaining { parameter ->
            run {
                val anonim = AnonimStatement.getNewAnonimStatement()
                ASTNodeService.solveASTNode(parameter as ASTNode, anonim)
                template.addParameters(anonim)
            }
        }
        println(templateDecl)
    }
}
