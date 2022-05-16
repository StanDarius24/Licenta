package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.DeclarationSpecifierParent
import com.stannis.dataModel.Statement

data class SimpleDeclSpecifier(
    override val `$type`: String? = "SimpleDeclSpecifier",
    var type: Int?,
    var isSigned: Boolean,
    var isUnsigned: Boolean,
    var isShort: Boolean,
    var isLong: Boolean,
    var isLongLong: Boolean,
    var isComplex: Boolean,
    var isImaginary: Boolean,
    var fDeclTypeExpression: Statement?,
    var isConstant: Boolean,
    var isExplicit: Boolean,
    var isFriend: Boolean,
    var isInline: Boolean,
    var isRestrict: Boolean,
    var isThreadLocal: Boolean,
    var isVirtual: Boolean,
    var isVolatile: Boolean,
    var declarationSpecifier: String?
) : Statement, DeclarationSpecifierParent
