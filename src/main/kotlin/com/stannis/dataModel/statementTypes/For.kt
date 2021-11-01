package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Method
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
    var body: Method?
): Statement {

    fun addInitializer(data: Initialization) {
        if(initializer == null) {
            initializer = ArrayList()
        }
        initializer!!.add(data)
    }

    fun addConditionExpression(data: Initialization) {
        if(conditionExpr == null) {
            conditionExpr = ArrayList()
        }
        conditionExpr!!.add(data)
    }

    fun addConditionDeclaration(data: Initialization) {
        if(conditionDecl == null) {
            conditionExpr = ArrayList()
        }
        conditionExpr!!.add(data)
    }

    fun addIteration(data: Initialization) {
        if(iteration == null) {
            iteration = ArrayList()
        }
        iteration!!.add(data)
    }

    fun addMethod(data: Method) {
        body = data
    }

}
