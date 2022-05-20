
using System;
using System.Collections.Generic;
using System.IO;

namespace Interpreter.Utility
{
    public static class Reader
    {
        public static string ReadFromPath(string path)
        {
            return File.ReadAllText(path);
        }

        public static IEnumerable<string> FindProjectData(string data)
        {
            if (!Directory.Exists(data)) return null;
            try
            {
                return Directory.GetFiles(StringService.SolveDataPath(data));
            }
            catch (Exception e)
            {
                Console.Out.Write(e);
                return null;
            }
        }
        
    }
}