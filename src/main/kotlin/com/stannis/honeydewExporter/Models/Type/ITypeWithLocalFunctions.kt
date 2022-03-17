package com.stannis.honeydewExporter.Models.Type

interface ITypeWithLocalFunctions : IType {
    var LocalFunctions: List<IMethodTypeWithLocalFunctions>
}
