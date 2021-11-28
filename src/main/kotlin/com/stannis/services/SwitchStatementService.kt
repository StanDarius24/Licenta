package com.stannis.services

import com.stannis.dataModel.Method
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSwitchStatement

class SwitchStatementService {

    fun solveSwitchStatement(data: CPPASTSwitchStatement, method: Method?) {
        println(data.rawSignature)
    }

}