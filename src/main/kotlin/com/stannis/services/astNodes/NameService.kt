package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.Name
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTName

object NameService {
    fun solveName(cppastName: CPPASTName, statement: Statement?) {
        StatementMapper.addStatementToStatement(statement!!, Name(cppastName.rawSignature))
    }
}
