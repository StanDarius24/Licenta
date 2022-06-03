package com.stannis.parser.metrics

import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*
import com.stannis.function.FunctionDeclaratorRegistry
import com.stannis.parser.visitor.ASTVisitorOverride

object Metrics {

    fun calculateCyclomaticComplexity() {
        if (ASTVisitorOverride.getPrimaryBlock().statements != null) {
            ASTVisitorOverride.getPrimaryBlock().statements!!.forEach { statement ->
                run {
                    if (statement is FunctionDefinition) {
                        var count = 1
                        if (statement.body != null) {
                            if ((statement.body!![0] as CompoundStatement).statements != null) {
                                (statement.body!![0] as CompoundStatement).statements!!.forEach {
                                    element ->
                                    run { count = checkCyclomaticType(element, count) }
                                }
                            }
                        }
                        addCyclomaticComplexToFunction(statement, count)
                    }
                }
            }
        }
    }

    private fun addCyclomaticComplexToFunction(statement: FunctionDefinition, count: Int) {
        if (statement.declarator != null) {
            if (FunctionDeclaratorRegistry.list != null) {
                val fc =
                    FunctionDeclaratorRegistry.list!!.find { functionDeclarator ->
                        (functionDeclarator.name as NameInterface).getWrittenName() ==
                            (statement.declarator!![0].name as NameInterface).getWrittenName()
                    }
                if (fc != null) {
                    fc.cyclomaticComplexity = count
                }
            }
        }
    }

    private fun checkCyclomaticType(element: Statement, count: Int): Int {
        when (element) {
            is While -> {
                return count + calculateWhileComplexity(element)
            }
            is If -> {
                return count + calculateIfComplexity(element)
            }
            is SwitchStatement -> {
                return count + calculateSwitchStatementComplexity(element)
            }
        }
        return count
    }

    private fun calculateSwitchStatementComplexity(element: SwitchStatement): Int {
        var count = 0
        if (element.body is CompoundStatement) {
            if ((element.body as CompoundStatement).statements != null) {
                (element.body as CompoundStatement).statements!!.forEach { statement ->
                    run {
                        count += checkCyclomaticType(statement, 0)
                        if (statement is CaseStatement) {
                            count += 1
                        }
                    }
                }
            }
        }
        return count
    }

    private fun calculateIfComplexity(element: If): Int {
        var count = 1
        if (element.thenClause is CompoundStatement) {
            if ((element.thenClause as CompoundStatement).statements != null) {
                (element.thenClause as CompoundStatement).statements!!.forEach { statement ->
                    run { count += checkCyclomaticType(statement, 0) }
                }
            }
        }
        if (element.elseClause != null) {
            count += checkCyclomaticType(element.elseClause as Statement, 0)
        }
        return count
    }

    private fun calculateWhileComplexity(element: While): Int {
        var count = 1
        if (element.body is CompoundStatement) {
            if ((element.body as CompoundStatement).statements != null) {
                (element.body as CompoundStatement).statements!!.forEach { statement ->
                    run { count += checkCyclomaticType(statement, 0) }
                }
            }
        }
        return count
    }
}
