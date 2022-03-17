package com.stannis.honeydewExporter.Models

import com.stannis.honeydewExporter.Models.Type.IEntityType
import com.stannis.honeydewExporter.Models.Type.INullableType
import com.stannis.honeydewExporter.Models.Type.ITypeWithAttributes

interface IParameterType : ITypeWithAttributes, INullableType {
    var Type: IEntityType
}
