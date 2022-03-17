package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.IMethodSignatureType

interface IAttributeType : IMethodSignatureType {
    var Type: IEntityType
    var Target: String
}
