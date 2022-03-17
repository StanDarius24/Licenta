package com.stannis.honeydewExporter.Models.Type

interface ITypeWithLocalVariables : IType {
    var LocalVariableTypes: List<ILocalVariableType>
}
