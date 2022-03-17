package com.stannis.honeydewExporter.Models.Type

interface IMethodSkeletonType :
    IMethodSignatureType,
    ICallingMethodsType,
    IContainedTypeWithAccessedFields,
    ITypeWithModifiers,
    ITypeWithCyclomaticComplexity,
    ITypeWithAttributes,
    ITypeWithMetrics,
    ITypeWithLinesOfCode,
    ITypeWithLocalVariables {}
