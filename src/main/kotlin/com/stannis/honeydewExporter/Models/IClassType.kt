package com.stannis.honeydewExporter.Models

import com.stannis.honeydewExporter.Models.Type.*

interface IClassType :
    IContainedType,
    ITypeWithGenericParameters,
    ITypeWithModifiers,
    ITypeWithAttributes,
    ITypeWithMetrics {
    var ClassType: String
    var FilePath: String
    var BaseTypes: List<IBaseType>
    var Imports: List<IImportType>
}
