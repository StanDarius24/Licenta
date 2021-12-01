package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Statement

data class DeleteExpression(
                            var deletedExpression: String?
): Statement
