package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.Name

data class ComplexCompositeTypeSpecifier(
    var our_class: CompositeTypeSpecifier,
    var path: String,
    var library: List<Name>
)
