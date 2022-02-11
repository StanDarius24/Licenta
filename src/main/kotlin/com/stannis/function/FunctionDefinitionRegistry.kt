package com.stannis.function

import com.stannis.dataModel.complexStatementTypes.DeclarationWithParent
import com.stannis.dataModel.complexStatementTypes.FunctionCallWithDeclaration
import com.stannis.dataModel.statementTypes.*
import org.eclipse.cdt.internal.core.dom.rewrite.astwriter.DeclaratorWriter

object FunctionDefinitionRegistry {

    var list: ArrayList<FunctionDefinition>? = null

    fun clearList() {
        list = null
    }

    fun addToList(data: FunctionDefinition) {
        if (list == null) {
            list = ArrayList()
        }
        val newFunctionDefinition = FunctionDefinition(data.declaratorSpecifier, data.declarator, null)
        removeUnwantedTypes(data, newFunctionDefinition)
        list!!.add(newFunctionDefinition)
        println()
    }

    private fun removeUnwantedTypes(oldFunctionDefinition: FunctionDefinition, newFunctionDefinition: FunctionDefinition) { // save only functionCalls, Declaration
        oldFunctionDefinition.body!!.iterator().forEachRemaining { statements -> run {
            if(statements is CompoundStatement) {
                removeIfBodyCompoundStatement(statements, newFunctionDefinition, oldFunctionDefinition)
            }
        } }
    }

    private fun removeIfBodyCompoundStatement(statements: CompoundStatement, newFunctionDefinition: FunctionDefinition, oldFunctionDefinition: FunctionDefinition) {
        statements.statements!!.iterator().forEachRemaining { statement -> run  {
            when (statement) {
                is FunctionCalls -> {
                    resolveWhenStatementIsFunctionCall(statement, newFunctionDefinition, oldFunctionDefinition)
                }
            }
        }}
    }

    private fun resolveWhenStatementIsFunctionCall(statement: FunctionCalls, newFunctionDefinition: FunctionDefinition, oldFunctionDefinition: FunctionDefinition) {
        SimpleDeclarationRegistry.list!!.iterator().forEachRemaining { simpleDeclaration -> run {
                    solveWhenDeclarationIsDeclarator(statement, simpleDeclaration, newFunctionDefinition, oldFunctionDefinition)
        }}
    }

    private fun solveWhenDeclarationIsDeclarator(statement: FunctionCalls, declarationWithParent: DeclarationWithParent, newFunctionDefinition: FunctionDefinition, oldFunctionDefinition: FunctionDefinition) {
        if(statement.name is IdExpression) {
            idExpressionSolver(statement, declarationWithParent, newFunctionDefinition, oldFunctionDefinition)
        } else if(statement.name is FieldReference) {
            fieldReferenceSolver(statement, declarationWithParent, newFunctionDefinition, oldFunctionDefinition)
        }
    }

    private fun idExpressionSolver(statement: FunctionCalls, declarationWithParent: DeclarationWithParent, newFunctionDefinition: FunctionDefinition, oldFunctionDefinition: FunctionDefinition) {
        declarationWithParent.declaration.declarators!!.iterator().forEachRemaining { declaration -> run {
            if(declaration is Declarator) {
                if ((statement.name as IdExpression).expression.toString().equals(declaration.name)) {
                    newFunctionDefinition.addToBody(FunctionCallWithDeclaration(oldFunctionDefinition, statement))
                }
            }
        } }
    }

    private fun fieldReferenceSolver(statement: FunctionCalls, declarationWithParent: DeclarationWithParent, newFunctionDefinition: FunctionDefinition, oldFunctionDefinition: FunctionDefinition) {
        declarationWithParent.declaration.declarators!!.iterator().forEachRemaining { declaration -> run {
            if(declaration is Declarator) {
                if((statement.name as FieldReference).fieldOwner is IdExpression) {
                    if (((statement.name as FieldReference).fieldOwner!! as IdExpression).expression.equals(declaration.name)) {
                        newFunctionDefinition.addToBody(FunctionCallWithDeclaration(oldFunctionDefinition, statement))
                    }
                } else if((statement.name as FieldReference).fieldOwner is FieldReference){
                    if((((statement.name as FieldReference).fieldOwner as FieldReference).fieldName as Name).name!!.equals(declaration.name)) {
                        newFunctionDefinition.addToBody(FunctionCallWithDeclaration(statement, declaration))
                    }
                } else if((statement.name as FieldReference).fieldName is Name) {
                    if(((statement.name as FieldReference).fieldName as Name).name.equals(declaration.name)) {
                        newFunctionDefinition.addToBody(FunctionCallWithDeclaration(statement, declaration))
                    }
                }
            }
        } }
    }

}