package com.stannis.honeydewExporter.Models.Type

interface IEntityType : INamedType {
    var IsExtern: Boolean
    var FullType: GenericType
}
