package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.Arguments
import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class FieldReference(
    override val `$type`: String? = "FieldReference",
    var fieldName: NameInterface?,
    var fieldOwner: Arguments?
) : Statement, Arguments, NameInterface
