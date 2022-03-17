package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.IGenericParameterType

interface ITypeWithGenericParameters : IType {
    var GenericParameters: List<IGenericParameterType>
}
