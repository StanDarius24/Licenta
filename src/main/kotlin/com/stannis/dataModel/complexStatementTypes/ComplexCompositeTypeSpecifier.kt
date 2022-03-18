package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier

data class ComplexCompositeTypeSpecifier(
    var our_class: CompositeTypeSpecifier,
    var path: String
)
