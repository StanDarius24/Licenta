package com.stannis.dataModel.complexStatementTypes

import com.stannis.dataModel.Statement
import com.stannis.dataModel.statementTypes.CompositeTypeSpecifier
import com.stannis.dataModel.statementTypes.Name

data class ComplexCompositeTypeSpecifier(
    override val `$type`: String? ="ComplexCompositeTypeSpecifier",
    var our_class: CompositeTypeSpecifier,
    var path: String,
    var library: List<Name>
): Statement {
    fun clone(): ComplexCompositeTypeSpecifier {
         return ComplexCompositeTypeSpecifier(this.`$type`, this.our_class, this.path, this.library)
    }
}
