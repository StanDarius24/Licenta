using System;

namespace Interpreter.Utility{
    public class OperatingSystem
    {
        public static string getOperatingSystem()
        {
            return Environment.OSVersion.Platform.ToString();
        }

        public static char getSeparator()
        {
            if (getOperatingSystem().Equals("Unix"))
            {
                return '/';
            } else if (getOperatingSystem().Contains("Win"))
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