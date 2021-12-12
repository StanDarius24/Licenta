package com.stannis.services.mapper

import com.stannis.dataModel.Class
import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*

class StatementMapper {
    companion object {

        fun addNameDependingOnType(stats: Statement, name: String) {
            when (stats) {
                is While -> {
                    stats.add(name)
                }
                is TypedefStructure -> {
                    stats.add(name)
                }
                is com.stannis.dataModel.statementTypes.Statement -> {
                    stats.add(name)
                }
                is FunctionCall -> {
                    stats.add(name)
                }
                is CPPMethodCall -> {
                    stats.add(name)
                }
            }
        }

        fun addFunctionCallDependingOnType(stats: Statement, fc: FunctionCall) {
            when (stats) {
                is FunctionCall -> {
                    stats.add(fc)
                }
                is If -> {
                    stats.add(fc)
                }
                is com.stannis.dataModel.statementTypes.Statement -> {
                    stats.add(fc)
                }
                is Return -> {
                    stats.add(fc)
                }
                is While -> {
                    stats.add(fc)
                }
            }
        }

        fun addStatementToStatement(statementParent: Statement, statementChild: Statement) {
            when(statementParent) {
                is Method -> {
                    statementParent.addStatement(statementChild)
                }
                is FunctionCall -> {
                    statementParent.addComplexParameters(statementChild)
                }
                is If -> {
                    statementParent.addStatement(statementChild)
                }
                is com.stannis.dataModel.statementTypes.Statement -> {
                    statementParent.addStatement(statementChild)
                }
                is Return -> {
                    statementParent.addStatement(statementChild)
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
                        statementParent.addMethod(statementChild as Method)
                    }
                }
                is ExpressionList -> {
                    println(statementParent)
                }
                is Class -> {
                    println(statementParent)
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
                else -> {
                    throw Exception()
                }
            }
        }

    }
}