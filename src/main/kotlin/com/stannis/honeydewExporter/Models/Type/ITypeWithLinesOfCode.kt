package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.LinesOfCode

interface ITypeWithLinesOfCode : IType {
    var loc: LinesOfCode
}
