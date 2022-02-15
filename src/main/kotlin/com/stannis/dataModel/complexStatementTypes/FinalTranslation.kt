package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionDefinition

data class FinalTranslation(
    var directives: ArrayList<Statement>?,
    var declaration: ArrayList<DeclarationWithParent>?,
    var functionCalls: ArrayList<FunctionDefinition>?,
    override val type: String = "FinalTranslation"
    ): Statement {

        fun addDirectives(statement: Statement) {
            if(directives == null) {
                directives = ArrayList()
            }
            directives!!.add(statement)
        }

    }
