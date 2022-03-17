package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.IContainedType
import com.stannis.honeydewExporter.Models.IMethodSignatureType

interface ICallingMethodsType : IContainedType {
    var CalledMethods: List<IMethodSignatureType>
}
