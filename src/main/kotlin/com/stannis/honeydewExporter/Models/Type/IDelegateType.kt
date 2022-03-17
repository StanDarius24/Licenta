package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.IClassType
import com.stannis.honeydewExporter.Models.IMethodSignatureType

interface IDelegateType : IClassType, IMethodSignatureType {
    var ReturnValue: IReturnValueType
}
