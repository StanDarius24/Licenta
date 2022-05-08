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
            } else if (getOperatingSystem().Equals("Windows"))
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