package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.FieldReference

data class FieldReferenceWithParent(override val `$type`: String? = "FieldReferenceWithParent",
                                    val fieldReference: FieldReference?,
                                    val parent: Statement?
                                    ): Statement
