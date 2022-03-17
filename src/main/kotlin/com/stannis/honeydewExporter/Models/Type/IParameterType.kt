package com.stannis.honeydewExporter.Models.Type

interface IParameterType : ITypeWithAttributes, INullableType {
    var Type: IEntityType
}
