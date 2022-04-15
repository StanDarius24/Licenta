package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.Name

data class ComplexCompositeTypeSpecifier(
    var our_class: CompositeTypeSpecifier,
    var path: String,
    var library: List<Name>
) {
    fun clone(): ComplexCompositeTypeSpecifier {
         return ComplexCompositeTypeSpecifier(this.our_class, this.path, this.library)
    }
}
