package com.stannis.honeydewExporter.Models

data class ProjectModel(
    var Name: String,
    var FilePath: String,
    var ProjectReferences: List<String>,
    var Namespaces: List<NamespaceModel>,
    var CompilationUnits: List<ICompilationUnitType>,
)
