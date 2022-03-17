package com.stannis.honeydewExporter.Models.Type

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
