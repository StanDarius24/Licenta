package com.stannis.honeydewExporter.Models

import com.stannis.honeydewExporter.Models.Type.IEntityType

interface IAttributeType : IMethodSignatureType {
    var Type: IEntityType
    var Target: String
}
