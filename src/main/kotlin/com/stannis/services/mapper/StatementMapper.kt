package com.stannis.services.mapper

import com.stannis.dataModel.PrimaryBlock
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*

class StatementMapper {
    companion object {
        fun addStatementToStatement(statementParent: Statement, statementChild: Statement) {
            when(statementParent) {
                is FunctionCall -> {
                    statementParent.addComplexParameters(statementChild)
                }
                is If -> {
                    statementParent.needAfix(statementChild)
                }
                is Return -> {
                    statementParent.retValue = statementChild
                }
                is SimpleTypeConstructorExpression -> {
                    statementParent.addParameter(statementChild)
                }
                is AnonimStatement -> {
                    statementParent.statement = statementChild
                }
                is For -> {
                    println(statementParent)
                    if(statementParent.initializer == null) {
                        statementParent.addInitializer(statementChild)
                    } else if(statementParent.conditionExpr == null) {
                        statementParent.addConditionExpression(statementChild)
                    } else if(statementParent.conditionDecl == null) {
                        statementParent.addConditionDeclaration(statementChild)
                    } else if(statementParent.iteration == null) {
                        statementParent.addIteration(statementChild)
                    } else {
                        statementParent.addBody(statementChild)
                    }
                }
                is ExpressionList -> {
                    statementParent.addStatement(statementChild)
                }
                is DoStatement -> {
                    if(statementParent.condition == null) {
                        statementParent.addToCondition(statementChild)
                    } else {
                        statementParent.addToBody(statementChild)
                    }
                }
                is Declarator -> {
                    statementParent.initialization = statementChild
                }
                is ConstructorInitializer -> {
                    statementParent.addStatement(statementChild)
                }
                is PrimaryBlock -> {
                    statementParent.addStatement(statementChild)
                }
                else -> {
                    throw Exception()
                }
            }
        }

    }
}