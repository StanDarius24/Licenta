package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

//data.initializerStatement // declaration Statement int x = 0;
//data.conditionExpression // conditia i<5 || calc();
//data.conditionDeclaration // declaration in condition
//data.iterationExpression // iteratii Binary expr / IdExpr etc..
//data.body // compound statement

data class For(
    var initializer: ArrayList<Initialization>?,
    var conditionExpr: ArrayList<Initialization>?,
    var conditionDecl: ArrayList<Initialization>?,
    var iteration: ArrayList<Initialization>?,
): Statement {
    override fun add(data: String) {
        TODO("Not yet implemented")
    }

    override fun add(data: FunctionCall) {
        TODO("Not yet implemented")
    }

}
