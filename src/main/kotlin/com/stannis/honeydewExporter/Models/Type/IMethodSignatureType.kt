package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.IParameterType

interface IMethodSignatureType : IContainedType {
    var ParameterTypes: List<IParameterType>
}
