using System;

namespace Interpreter.Utility
{
    public class Reader
    {

        public static String ReadFromPath(String path)
        {
            return System.IO.File.ReadAllText(path);
        }
    }
}