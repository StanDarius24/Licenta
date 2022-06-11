package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclarationParent
import com.stannis.dataModel.DeclarationSpecifierParent
import com.stannis.dataModel.Statement

data class FunctionDefinition(
    override val `$type`: String? = "FunctionDefinition",
    var declaratorSpecifier: DeclarationSpecifierParent?,
    var declarator: ArrayList<FunctionDeclarator>?,
    var body: ArrayList<Statement>?,
    var cyclomaticComplexity: Int,
    var modifier: String,
    var namespace: String?
) : Statement, DeclarationParent {

    fun setCyclomatic(number: Int) {
        cyclomaticComplexity = number
    }
    fun addDeclarator(statement: Statement) {
        if (declarator == null) {
            declarator = ArrayList()
        }
        declarator!!.add(statement as FunctionDeclarator)
    }
    fun addToBody(statement: Statement) {
        if (body == null) {
            body = ArrayList()
        }
        body!!.add(statement)
    }
}
