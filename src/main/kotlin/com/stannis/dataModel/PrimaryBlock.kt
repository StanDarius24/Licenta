package com.stannis.dataModel

data class PrimaryBlock(
    var statement: Statement?,
    override val type: String? = "PrimaryBlock"
):Statement
