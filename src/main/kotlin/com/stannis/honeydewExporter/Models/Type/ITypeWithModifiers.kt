package com.stannis.honeydewExporter.Models.Type

interface ITypeWithModifiers : IType {
    var AccessModifier: String
    var Modifier: String
}
