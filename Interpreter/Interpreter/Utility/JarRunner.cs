using System;
using System.Diagnostics;
using System.Linq;

namespace Interpreter.Utility{
    public class JarRunner
    {
        public static void RunJar(string path)
        {
            var process = Process.GetCurrentProcess(); // Or whatever method you are using
            var fullPath = process.MainModule.FileName;
            
            var list = fullPath.Split(OperatingSystem.GetSeparator());
            list = list.Take(Array.IndexOf(list, "Interpreter")).ToArray();

            var projectPath = string.Join(OperatingSystem.GetSeparator().ToString(), list);
        
            var processInfo = new ProcessStartInfo("java.exe", "-jar " + projectPath + OperatingSystem.GetSeparator() + "target" + OperatingSystem.GetSeparator() + "KotlinLicenta-1.0-SNAPSHOT-jar-with-dependencies.jar")
            {
                CreateNoWindow = true,
                UseShellExecute = false
            };
            Process proc;

            if ((proc = Process.Start(processInfo)) == null)
            {
                throw new InvalidOperationException("??");
            }

            proc.WaitForExit();
            var exitCode = proc.ExitCode;
            proc.Close();
        }
    }
}