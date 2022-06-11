package com.stannis.parser.metrics

import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.*
import com.stannis.function.FunctionDeclaratorRegistry
import com.stannis.function.NameSpaceRegistry
import com.stannis.services.astNodes.TranslationUnitService

object Metrics {

    fun calculateCyclomaticComplexity(filepath: String) {
        if (TranslationUnitService.translationUnitCache.listOfDeclarations != null) {
            TranslationUnitService.translationUnitCache.listOfDeclarations!!.forEach { statement ->
                run {
                    if (statement is FunctionDefinition) {
                        solveFunctionDefinition(statement, false)
                    } else if (statement is SimpleDeclaration &&
                            statement.declSpecifier is CompositeTypeSpecifier
                    ) {
                        solveClassDefinition(statement)
                    }
                }
            }
        }
    }

    private fun solveClassDefinition(statement: SimpleDeclaration) {
        if ((statement.declSpecifier as CompositeTypeSpecifier).declarations != null) {
            (statement.declSpecifier as CompositeTypeSpecifier).declarations!!.forEach {
                declarationParent ->
                run {
                    if (declarationParent is FunctionDefinition) {
                        solveFunctionDefinition(declarationParent, true)
                    }
                }
            }
        }
    }

    fun solveFunctionDefinition(statement: FunctionDefinition, bool: Boolean) {
        var count = 1
        if (statement.body != null) {
            if ((statement.body!![0] as CompoundStatement).statements != null) {
                (statement.body!![0] as CompoundStatement).statements!!.forEach { element ->
                    run { count = checkCyclomaticType(element, count) }
                }
            }
        }
        if (!bool) {
            addCyclomaticComplexToFunction(statement, count)
            statement.setCyclomatic(count)
        } else {
            NameSpaceRegistry.listOfNameSpace.forEach { nameSpace ->
                run {
                    if (nameSpace.declarations != null) {
                        nameSpace.declarations!!.forEach { decl ->
                            run {
                                if (decl is SimpleDeclaration &&
                                        decl.declSpecifier is CompositeTypeSpecifier
                                ) {
                                    val stat =
                                        (decl.declSpecifier as CompositeTypeSpecifier)
                                            .declarations!!.find { declInClass ->
                                                declInClass == statement
                                            }
                                    if (stat != null) {
                                        (stat as FunctionDefinition).setCyclomatic(count)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            statement.setCyclomatic(count)
        }
    }

    private fun addCyclomaticComplexToFunction(statement: FunctionDefinition, count: Int) {
        if (statement.declarator != null) {
            val fc =
                FunctionDeclaratorRegistry.list.find { functionDeclarator ->
                    (functionDeclarator.name as NameInterface).getWrittenName() ==
                        (statement.declarator!![0].name as NameInterface).getWrittenName()
                }
            if (fc != null) {
                fc.cyclomaticComplexity = count
            } else {
                val fcx =
                    NameSpaceRegistry.listOfNameSpace.find { nameSpace ->
                        nameSpace.declarations!!.contains(statement)
                    }
                if (fcx != null) {
                    fcx.declarations!!
                        .find { element -> element == statement }
                        ?.let { (it as FunctionDefinition).setCyclomatic(count) }
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
            is For -> {
                return count + calculateForComplexity(element)
            }
        }
        return count
    }

    private fun calculateForComplexity(element: For): Int {
        var count = 1
        if(element.body != null) {
            element.body!!.forEach { ev -> run {
                count += checkCyclomaticType(ev, count)
            }}
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
