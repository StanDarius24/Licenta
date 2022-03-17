package com.stannis.honeydewExporter.Models.Type

data class GenericType(
    var Name: String,
    override var IsNullable: Boolean,
    var ContainedTypes: List<GenericType>
) : INullableType
