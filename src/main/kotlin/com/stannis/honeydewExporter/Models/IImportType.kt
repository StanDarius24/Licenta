package com.stannis.honeydewExporter.Models

import com.stannis.honeydewExporter.Models.Type.INamedType

interface IImportType : INamedType {
    var Alias: String
    var AliasType: String
    var IsStatic: Boolean
}

enum class EAliasType {
    None,
    Namespace,
    Class,
    NotDetermined
}
