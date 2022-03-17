package com.stannis.honeydewExporter.Models.Type

interface IGenericParameterType : INamedType, ITypeWithAttributes {
    var Modifier: String
    var Constraints: List<IEntityType>
}
