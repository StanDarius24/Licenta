package com.stannis.services

import com.stannis.dataModel.Statement
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTConditionalExpression

class ConditionalExpressionService {

    fun solveConditionalExpression(conditionalExpression: CPPASTConditionalExpression, statement: Statement) {
        conditionalExpression.logicalConditionExpression // condition
        conditionalExpression.positiveResultExpression // true
        conditionalExpression.negativeResultExpression // false
    }
}