
using System.Collections.Generic;
using System.IO;

namespace Interpreter.Utility
{
    public static class Reader
    {
        public static string ReadFromPath(string path)
        {
            return System.IO.File.ReadAllText(path);
        }

        public static IEnumerable<string> FindProjectData(string data)
        {
            if (Directory.Exists(data))
            {
                return Directory.GetFiles(
                    @StringService.SolveDataPath(data) +
                    OperatingSystem.GetSeparator());
            }
            return null;
        }
        
    }
}