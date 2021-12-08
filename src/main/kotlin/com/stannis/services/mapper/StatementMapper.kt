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
                is Initialization -> {
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
                is Initialization -> {
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
                is Initialization -> {
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

                }
                is ExpressionList -> {

                }
                is Class -> {

                }
                else -> {
                    throw Exception()
                }
            }
        }

    }
}