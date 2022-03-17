package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.IClassType

interface IMembersClassType : IClassType, ITypeWithLinesOfCode, ITypeWithDestructor {
    var Fields: List<IFieldType>
    var Constructors: List<IConstructorType>
    var Methods: List<IMethodType>
}
