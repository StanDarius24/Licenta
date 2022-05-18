package com.stannis.dataModel.statementTypes

import com.stannis.dataModel.NameInterface
import com.stannis.dataModel.Statement

data class Capture(
    override val `$type`: String? = "Capture",
    var byReference: Boolean?,
    var identifier: NameInterface?
): Statement
