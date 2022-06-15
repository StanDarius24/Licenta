using System;
using System.Collections.Generic;
using System.Linq;
using ClosedXML.Excel;
using Interpreter.Models.metrics;
using Interpreter.services.metrics;

namespace Interpreter.Utility
{
    public static class Exporter
    {
        public static bool Export(List<MetricsModel> list, string file, string sheetName)
        {
            var exporter = false;

            using (IXLWorkbook workbook = new XLWorkbook())
            {
                workbook.AddWorksheet(sheetName).FirstCell().InsertTable(list, false);
                workbook.SaveAs(file);
                exporter = true;
            }

            return exporter;
        }

        public static void CreateMetricFile(string solveDataPath)
        {
            var totalList = new List<MetricsModel>();
            foreach (var metricsInFile in MetricsRegistry.metricsList)
            {
                totalList.AddRange(CreateListOfMetrics(metricsInFile));
            }

            Export(totalList, solveDataPath + OperatingSystem.GetSeparator() + "Metrics.xlsx", "Metrics");
        }

        private static List<MetricsModel> CreateListOfMetrics(MetricsInFile filler)
        {
            var newList = new List<MetricsModel>();
            AddExternal(newList, filler.ExternalClasses);
            AddClasses(newList, filler.classMetrics);
            AddNamespaces(newList, filler.nameSpaceMetrics);
            return newList;
        }

        private static void AddNamespaces(List<MetricsModel> newList, IList<MetricsInFile> fillerNameSpaceMetrics)
        {
            foreach (var fillerNameSpaceMetric in fillerNameSpaceMetrics)
            {
                AddExternal(newList, fillerNameSpaceMetric.ExternalClasses);
                AddClasses(newList, fillerNameSpaceMetric.classMetrics);
            }
        }

        private static void AddClasses(List<MetricsModel> newList, IList<MetricsAditionalData> fillerClassMetrics)
        {
            newList.AddRange(fillerClassMetrics.Select(variableClassMetric => variableClassMetric.metricsModel));
        }

        private static void AddExternal(List<MetricsModel> newList, MetricsAditionalData fillerExternalClasses)
        {
            newList.Add(fillerExternalClasses.metricsModel);
        }
    }
}