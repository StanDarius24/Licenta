using System.Collections.Generic;
using System.Linq;

namespace Interpreter.Utility{
    public class StringService
    {
        public static string FixPath(string path)
        {
            var name = path.Split(OperatingSystem.GetSeparator()).Last();
            return name.Contains('.') ? name.Split('.')[0] : name;
        }
        
        public static string FixHeaderName(string headerName)
        {
            var list = headerName.Split('\"');
            if (list.Length > 2)
            {
                return list[list.Length - 2];
            }
            else
            {
                var returnedHeaderName = headerName.Substring(headerName.IndexOf('<') + 1);
                string newReturn;
                newReturn = returnedHeaderName.Contains('>') ? returnedHeaderName.Substring(0, returnedHeaderName.IndexOf('>')) : returnedHeaderName;
                return newReturn.Contains("/") ? newReturn.Split('/')[1] : newReturn;
            }
        }
        
        public static string DeleteExtension(string headerFileName)
        {
            return FixHeaderName(headerFileName).Split('.')[0];
        }
        
        public static string GetDirectoryPath(string path)
        {
            var listx = path.Split(OperatingSystem.GetSeparator());
            listx = listx.Take(listx.Length - 1).ToArray();
            return string.Join(OperatingSystem.GetSeparator().ToString(), listx);
        }

        public static string SolveDataPath(string data)
        {
            var list = data.Split(OperatingSystem.GetSeparator());
            var last = list[list.Length-1];
            list = list.Take(list.Length - 1).ToArray();
            list = list.Append("result").ToArray();
            list = list.Append(last).ToArray();
            return string.Join(OperatingSystem.GetSeparator().ToString(), list);
        }
    }
    
}