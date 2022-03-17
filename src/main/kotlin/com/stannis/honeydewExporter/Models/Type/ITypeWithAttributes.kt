package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.IAttributeType

interface ITypeWithAttributes : IType {
    var Attributes: List<IAttributeType>
}
