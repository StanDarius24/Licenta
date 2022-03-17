package com.stannis.honeydewExporter.Models.Type

interface IFieldType :
    IContainedType, ITypeWithModifiers, ITypeWithAttributes, ITypeWithMetrics, INullableType {
    var IsEvent: Boolean
    var Type: IEntityType
}
