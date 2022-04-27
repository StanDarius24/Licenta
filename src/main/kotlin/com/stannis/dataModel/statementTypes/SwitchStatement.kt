package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class SwitchStatement(
    override val `$type`: String? = "SwitchStatement",
    var controllerExpression: Statement?,
    var body: Statement?
) : Statement {
    fun addControllerExpression(statement: Statement?) {
        controllerExpression = statement
    }

    fun addBody(statement: Statement?) {
        body = statement
    }
}
