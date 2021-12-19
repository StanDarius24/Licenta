package com.stannis.services

import com.stannis.dataModel.Statement
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSwitchStatement

object SwitchStatementService {

    fun solveSwitchStatement(data: CPPASTSwitchStatement, statement: Statement?) {
        println(data.rawSignature)
    }

}