package com.stannis.honeydewExporter.Models.Type

interface IMethodType : IMethodSkeletonType, ITypeWithGenericParameters {
    var ReturnValue: IReturnValueType
}
