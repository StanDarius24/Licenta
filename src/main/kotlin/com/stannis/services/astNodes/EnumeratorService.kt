package com.stannis.services.astNodes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.Enumerator
import com.stannis.services.mapper.StatementMapper
import org.eclipse.cdt.internal.core.dom.parser.ASTNode
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTEnumerator

object EnumeratorService {

    fun solveEnumerator(cppastEnumerator: CPPASTEnumerator, statement: Statement?) {
        StatementMapper.addStatementToStatement(statement!!,
            Enumerator(
            if(cppastEnumerator.name != null) {
                cppastEnumerator.name.rawSignature}
            else {
                ""
            }
          ,
            if(cppastEnumerator.value != null) {
                cppastEnumerator.value.rawSignature
            } else {
                ""
            }))
    }

}