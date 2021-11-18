package com.stannis.services

import com.stannis.dataModel.Antet
import com.stannis.dataModel.Class
import com.stannis.dataModel.Declaration
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ClassService {

    private var functionCallsService = FunctionCallsService()
    private var methodService = MethodService()
    private var functionDefService = FunctionDefinitionService()
    private lateinit var defaulType: String

    fun parseDecl(classDeclaration: Class, declSpecifier: CPPASTCompositeTypeSpecifier) {
        println(declSpecifier)
        declSpecifier.members.iterator().forEachRemaining { member ->
            run {
                when (member) {
                    is CPPASTVisibilityLabel -> {
                        defaulType = member.rawSignature
                        println(member.rawSignature)
                    }
                    is CPPASTSimpleDeclaration -> {
                        getDeclarationsForClass(member, classDeclaration)
                    }
                    is CPPASTFunctionDefinition -> {
                        handleCPPASTFunctionDefinition(classDeclaration, member)
                    }
                }
            }
        }
        println("da")
    }

    private fun handleCPPASTFunctionDefinition(classDeclaration: Class, member: CPPASTFunctionDefinition) {
        val method = methodService.createMethod()
        classDeclaration.addMethod(method)
        methodService.setAntet(method,
        Antet(
                member.declSpecifier.rawSignature,
                member.declarator.name.rawSignature,
                functionDefService.getParametersDeclarationArray((member.declarator as CPPASTFunctionDeclarator).parameters)
                )
        )

        (member.body as CPPASTCompoundStatement).statements
            .iterator().forEachRemaining { data: IASTStatement -> CoreParserClass.seeCPASTCompoundStatement(data, method) }
    }

    private fun getDeclarationsForClass(member: CPPASTSimpleDeclaration, classDeclaration: Class) {
        println(defaulType)
        member.declarators.iterator().forEachRemaining { data ->
            val decl = Declaration(
                data.name.rawSignature,
                member.declSpecifier.rawSignature,
                data.pointerOperators.size == 1,
                null,
                if (data is CPPASTArrayModifier ) {
                    (data as CPPASTArrayDeclarator).arrayModifiers[0].constantExpression
                        .rawSignature.toInt()
                } else { 0 },
                defaulType
            )
            functionCallsService.getFunctionCall(data, decl)
            classDeclaration.addDeclaration(decl)
        }
    }

}