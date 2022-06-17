package com.stannis.services.astNodes

import com.stannis.dataModel.DeclarationSpecifierParent
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*
import com.stannis.function.*
import com.stannis.parser.metrics.Metrics
import com.stannis.services.cppastService.ASTNodeService
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDefinition
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTNamespaceDefinition

object FunctionDefinitionService {

    var name: String? = ""
    fun solveFunctionDefinition(funcDef: CPPASTFunctionDefinition, statement: Statement?) {
        //        FunctionCallsRegistry.listOfFunctionCalls.clear()
        //        DeclarationRegistry.listOfDeclaration.clear()
        val functionDefinition = setFunction(funcDef)
        val anonimStatement3 = AnonimStatement.getNewAnonimStatement()
        if (funcDef.body != null) {
            ASTNodeService.solveASTNode(funcDef.body as ASTNode, anonimStatement3)
        }
        functionDefinition.addToBody(anonimStatement3.statement as Statement)
        val parent = ParentExtractor.extractNameSpace(funcDef)
        name =
            if (parent != null) {
                (parent as CPPASTNamespaceDefinition).name.rawSignature
            } else {
                null
            }
        solveBody(functionDefinition)
        FunctionDefinitionRegistry.listFromTranslationUnit.add(functionDefinition)
        StatementMapper.addStatementToStatement(statement!!, functionDefinition)
    }

    private fun solveBody(functionDefinition: FunctionDefinition) {
        Metrics.solveFunctionDefinition(functionDefinition, false)
        val functionDefinitionOOp =
            FunctionDefinition(
                declaratorSpecifier = functionDefinition.declaratorSpecifier,
                declarator = functionDefinition.declarator,
                cyclomaticComplexity = functionDefinition.cyclomaticComplexity,
                modifier = functionDefinition.modifier,
                body = ArrayList(),
                namespace = name
            )
        println()
        DeclarationRegistry.listOfDeclaration.forEach { element ->
            run { functionDefinitionOOp.body!!.add(element) }
        }

        FunctionCallsRegistry.listOfFunctionCalls.forEach { element ->
            run {
                val new = FunctionCallsRegistry.generateFunctionWithParent(element, functionDefinition)
                if (new != null) {
                functionDefinitionOOp.body!!.add(new)
                    } else {
                    functionDefinitionOOp.body!!.add(element)
                }
            }
        }

        FieldReferenceRegistry.listOfFieldReference.forEach { element ->
            run {
                FieldReferenceRegistry.checkFieldAndGenerate(element, functionDefinition)
                functionDefinitionOOp.body!!.add(element)
            }
        }
        FunctionCallsRegistry.listOfFunctionCalls.clear()
        DeclarationRegistry.listOfDeclaration.clear()
        FieldReferenceRegistry.listOfFieldReference.clear()
        FunctionDefinitionRegistry.listOfDefinitionOOP.add(functionDefinitionOOp)
    }

    fun setFunction(funcDef: CPPASTFunctionDefinition): FunctionDefinition {
        val functionDefinition =
            FunctionDefinition(
                declaratorSpecifier = null,
                declarator = null,
                body = null,
                cyclomaticComplexity = 1,
                modifier = ASTNodeService.modifier,
                namespace = name
            )
        val anonimStatement1 = AnonimStatement.getNewAnonimStatement()
        if (funcDef.declSpecifier != null) {
            ASTNodeService.solveASTNode(funcDef.declSpecifier as ASTNode, anonimStatement1)
        }
        functionDefinition.declaratorSpecifier =
            anonimStatement1.statement as DeclarationSpecifierParent
        val anonimStatement2 = AnonimStatement.getNewAnonimStatement()
        if (funcDef.declarator != null) {
            ASTNodeService.solveASTNode(funcDef.declarator as ASTNode, anonimStatement2)
        }
        functionDefinition.addDeclarator(anonimStatement2.statement as Statement)
        return functionDefinition
    }
}
