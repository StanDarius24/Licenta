package com.stannis.services

import com.stannis.dataModel.Method
import com.stannis.dataModel.Statement
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTSwitchStatement

class SwitchStatementService {

    companion object{
        private lateinit var switchStatementService: SwitchStatementService

        fun getInstance(): SwitchStatementService{
            if(!::switchStatementService.isInitialized) {
                switchStatementService = SwitchStatementService()
            }
            return switchStatementService
        }
    }

    fun solveSwitchStatement(data: CPPASTSwitchStatement, statement: Statement?) {
        println(data.rawSignature)
    }

}