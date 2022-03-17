package com.stannis.honeydewExporter.Models

data class RepositoryModel(
    var Version: String,
    var Solutions: List<SolutionModel>,
    var Projects: List<ProjectModel>
)
