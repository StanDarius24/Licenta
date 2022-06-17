package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FunctionCalls

data class FunctionCallsWithDeclaration(override val `$type`: String? = "FunctionCallsWithDeclaration",
                                        var functionCalls: FunctionCalls?,
                                        var declWithParent: Statement?
                                        ):Statement
