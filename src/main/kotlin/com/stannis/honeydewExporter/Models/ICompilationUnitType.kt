package com.stannis.honeydewExporter.Models

import com.stannis.honeydewExporter.Models.Type.ITypeWithLinesOfCode
import com.stannis.honeydewExporter.Models.Type.ITypeWithMetrics

interface ICompilationUnitType : ITypeWithLinesOfCode, ITypeWithMetrics {
    var ClassType: List<IClassType>
    var FilePath: String
    var Imports: List<IImportType>
}
