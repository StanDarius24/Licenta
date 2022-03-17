package com.stannis.honeydewExporter.Models.Type

import com.stannis.honeydewExporter.Models.MetricModel

interface ITypeWithMetrics : IType {
    var Metrics: List<MetricModel>
}
