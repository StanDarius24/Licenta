using System;

namespace Interpreter.Utility{
    public class OperatingSystem
    {
        public static string GetOperatingSystem()
        {
            return Environment.OSVersion.Platform.ToString();
        }

        public static char GetSeparator()
        {
            if (GetOperatingSystem().Equals("Unix"))
            {
                return '/';
            } else if (GetOperatingSystem().Contains("Win"))
            {
                return '\\';
            }
            else
            {
                return '/';
            }
        }
    }
};