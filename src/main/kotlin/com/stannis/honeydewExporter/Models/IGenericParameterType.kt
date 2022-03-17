package com.stannis.honeydewExporter.Models

import com.stannis.honeydewExporter.Models.Type.IEntityType
import com.stannis.honeydewExporter.Models.Type.INamedType
import com.stannis.honeydewExporter.Models.Type.ITypeWithAttributes

interface IGenericParameterType : INamedType, ITypeWithAttributes {
    var Modifier: String
    var Constraints: List<IEntityType>
}
