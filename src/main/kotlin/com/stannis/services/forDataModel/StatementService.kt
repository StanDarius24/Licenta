package com.stannis.services.forDataModel

import com.stannis.dataModel.Statement

class StatementService {

    private var statement = Statement(null, null)

    fun addContent(content: String) {
        statement.content = content
    }

    fun addToken(token: String) {
        statement.Token?.add(token)
    }

    fun getStatement() :Statement {
        return statement
    }
}