package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.IContainedType

interface IContainedTypeWithAccessedFields : IContainedType {
    var AccessedFields: List<AccessedField>
}
