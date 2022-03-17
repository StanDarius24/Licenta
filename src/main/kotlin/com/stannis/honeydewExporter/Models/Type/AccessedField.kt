package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.IContainedType

data class AccessedField(
    var Name: String,
    override var ContainingTypeName: String,
    var Kind: AccessKind
) : IContainedType

enum class AccessKind {
    Getter,
    Setter
}
