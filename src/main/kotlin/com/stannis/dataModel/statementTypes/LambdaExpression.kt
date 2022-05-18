package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class LambdaExpression(
    override val `$type`: String? = "LambdaExpression",
    var captureDefault: NameInterface?,
    var captures: ArrayList<Statement>?,
    var declarator: Statement?,
    var body: Statement?,
    var closureTypeName: Statement?,
    var implicitFunctionCallName: ArrayList<Statement>?
) : Statement, Arguments {
    fun addImplicitFunctionCallName(statement: Statement?) {
        if(implicitFunctionCallName == null) {
            implicitFunctionCallName = ArrayList()
        }
        implicitFunctionCallName!!.add(statement!!)
    }

    fun addCaptures(statement: Statement?) {
        if (captures == null) {
            captures = ArrayList()
        }
        captures!!.add(statement!!)
    }
}
