package com.stannis.function

import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.complexStatementTypes.FieldReferenceWithParent
import com.stannis.dataModel.statementTypes.*

object FieldReferenceRegistry {
    fun checkFieldAndGenerate(element: FieldReference, functionDefinition: FunctionDefinition): FieldReferenceWithParent? {
        FunctionCallsRegistry.listOfFunctionCalls.forEach { functionCalls -> run {
            if (functionCalls.name == element) {
                return null
            }
        } }

        return findParent(element, functionDefinition)
    }

    private fun findParent(element: FieldReference, functionDefinition: FunctionDefinition): FieldReferenceWithParent? {
        DeclarationRegistry.listOfDeclaration.forEach { declWithParent -> run {
            if (declWithParent.declaration is DeclarationStatement) {
                if ((declWithParent.declaration as DeclarationStatement).declarations != null && (declWithParent.declaration as DeclarationStatement).declarations!!.isNotEmpty()) {
                    if ((declWithParent.declaration as DeclarationStatement).declarations!![0] is SimpleDeclaration) {
                        val simpleDecl = (declWithParent.declaration as DeclarationStatement).declarations!![0] as SimpleDeclaration
                        if (simpleDecl.declarators != null && simpleDecl.declarators!!.isNotEmpty()) {
                            if (element.fieldOwner is IdExpression && simpleDecl.declarators!![0] is Declarator) {
                                if ((element.fieldOwner as IdExpression).expression!!.getWrittenName() == (simpleDecl.declarators!![0] as Declarator).name) {
                                    return FieldReferenceWithParent(fieldReference = element, parent = declWithParent.declaration)
                                }
                            }
                        }
                    }
                }
            }
        }}
        if (functionDefinition.declarator != null && functionDefinition.declarator!!.isNotEmpty()) {
            if (functionDefinition.declarator!![0].parameter != null && functionDefinition.declarator!![0].parameter!!.isNotEmpty()) {
                functionDefinition.declarator!![0].parameter!!.forEach { parameterDeclaration -> run {
                    if (element.fieldOwner is IdExpression) {
                    if (((element.fieldOwner as IdExpression).expression as NameInterface).getWrittenName() == (parameterDeclaration.declarator as Declarator).name) {
                        return FieldReferenceWithParent(fieldReference = element, parent = parameterDeclaration)
                    }}
                } }
            }
        }
        SimpleDeclarationRegistry.globalDeclaration.forEach { declarationWithParent -> run {
            if (element.fieldOwner is IdExpression) {
            if ((declarationWithParent.declaration.declarators!![0] as Declarator).name == ((element.fieldOwner as IdExpression).expression as NameInterface).getWrittenName()) {
                return FieldReferenceWithParent(fieldReference = element, parent = declarationWithParent.declaration)
            }
                }
        }  }
        return null
    }

    var listOfFieldReference: ArrayList<FieldReference> = ArrayList()

}