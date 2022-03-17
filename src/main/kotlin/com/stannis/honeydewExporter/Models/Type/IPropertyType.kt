package com.stannis.honeydewExporter.Models.Type

interface IPropertyType : IFieldType, ITypeWithCyclomaticComplexity, ITypeWithLinesOfCode {
    var Accessors: List<IMethodType>
}
