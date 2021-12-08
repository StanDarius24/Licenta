package com.stannis.services

import com.stannis.dataModel.Antet
import com.stannis.dataModel.Class
import com.stannis.dataModel.Declaration
import com.stannis.services.cppastService.ASTNodeService
import org.eclipse.cdt.core.dom.ast.IASTStatement
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.*

class ClassService {

    companion object{
        private lateinit var classService: ClassService

        fun getInstance(): ClassService{
            if(!::classService.isInitialized) {
                classService = ClassService()
            }
            return classService
        }
    }

    private var defaulType = "public:"

    fun parseDecl(classDeclaration: Class, declSpecifier: CPPASTCompositeTypeSpecifier) {
        if(declSpecifier.baseSpecifiers != null) {
            declSpecifier.baseSpecifiers.iterator().forEachRemaining {
                inher ->
                run {
                    classDeclaration.addInheritance(inher.nameSpecifier.rawSignature)
                }
            }
        }
        declSpecifier.members.iterator().forEachRemaining { member ->
            run {

                ASTNodeService.getInstance()
                    .solveASTNode(member as ASTNode, classDeclaration)
//                when (member) {
//                    is CPPASTVisibilityLabel -> {
//                        defaulType = member.rawSignature
//                    }
//                    is CPPASTSimpleDeclaration -> { // ASTAttributeOwner //TODO IMPORTANT different class
//                        getDeclarationsForClass(member, classDeclaration)
//                    }
//                    is CPPASTFunctionDefinition -> {
//                        handleCPPASTFunctionDefinition(classDeclaration, member)
//                    }
//                    is CPPASTTemplateDeclaration -> {
//                        println(member)
//                        //TODO
//                    }
//                    is CPPASTProblemDeclaration -> {
//                        println(member)
//                        //TODO
//                    }
//                    is CPPASTStaticAssertionDeclaration -> {
//                        println(member)
//                        //TODO
//                    }
//                    is CPPASTAliasDeclaration -> {
//                        println(member)
//                        //TODO
//                    }
//                    else -> { throw Exception() }
//                }
            }
        }
    }

    private fun handleCPPASTFunctionDefinition(classDeclaration: Class, member: CPPASTFunctionDefinition) {
        val method = MethodService.getInstance().createMethod()
        classDeclaration.addMethod(method)
        MethodService.getInstance().setAntet(method,
        Antet(
                member.declSpecifier.rawSignature,
                member.declarator.name.rawSignature,
                FunctionDefinitionService.getInstance()
                    .getParametersDeclarationArray((member.declarator as CPPASTFunctionDeclarator).parameters)
                )
        )
        method.modifier = defaulType
        if(member.body != null) {
            if (member.body is CPPASTCompoundStatement) {
                if ((member.body as CPPASTCompoundStatement).statements != null) {
                    (member.body as CPPASTCompoundStatement).statements
                        .iterator()
                        .forEachRemaining { data: IASTStatement ->
                            CoreParserClass.seeCPASTCompoundStatement(
                                data,
                                method
                            )
                        }
                }
            } else {
                throw Exception()
            }
        }
    }

    private fun getDeclarationsForClass(member: CPPASTSimpleDeclaration, classDeclaration: Class) {
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
            FunctionCallsService.getInstance().getFunctionCall(data, decl)
            classDeclaration.addDeclaration(decl)
        }
    }

}