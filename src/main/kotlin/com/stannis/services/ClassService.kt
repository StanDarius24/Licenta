package com.stannis.services

import com.stannis.dataModel.Class
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTCompositeTypeSpecifier
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSimpleDeclaration
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTVisibilityLabel

class ClassService {

    var declStatementParser = DeclarationStatementParser()
    var methodService = MethodService()
    var functDefinitionService = FunctionDefinitionService()

    fun parseDecl(classDeclaration: Class, declSpecifier: CPPASTCompositeTypeSpecifier) {
        println(declSpecifier)
        val method = methodService.createMethod()
        declSpecifier.members.iterator().forEachRemaining { member ->
            run {
                    if(member is CPPASTVisibilityLabel) {
                        println(member.rawSignature)
                    } else if(member is CPPASTSimpleDeclaration) {
                        declStatementParser.declStatement(member, method)
                    } else if(member is CPPASTFunctionDefinition) {
                        functDefinitionService.handleCPPASTFunctionDefinition(member, method)
                    }
            }
        }
    }
}