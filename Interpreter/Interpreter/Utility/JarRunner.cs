using System;
using System.Diagnostics;
using System.Linq;
using System.Threading;

namespace Interpreter.Utility
{
    public class JarRunner
    {
        public static void RunJar(string path)
        {
            var process = Process.GetCurrentProcess();
            if (process.MainModule == null) return;
            var fullPath = process.MainModule.FileName;
            var list = fullPath.Split(OperatingSystem.GetSeparator());
            list = list.Take(Array.IndexOf(list, "Interpreter")).ToArray();
            var projectPath = string.Join(OperatingSystem.GetSeparator().ToString(), list);
            var processInfo = new ProcessStartInfo("java.exe",
                $"-jar {projectPath}{OperatingSystem.GetSeparator()}target{OperatingSystem.GetSeparator()}KotlinLicenta-1.0-SNAPSHOT-jar-with-dependencies.jar {path} oop")
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
            if (exitCode != 0)
            {
                if (exitCode == 1)
                {
                    Console.WriteLine("Jar Not CREATED!");
                    CreateJarFile(projectPath, path);
                }
            }

            proc.Close();
        }

        private static void CreateJarFile(string path, string projectPath)
        {
            var process = new Process
            {
                StartInfo =
                {
                    FileName = "mvn",                        
                    WorkingDirectory = $@"{path}",
                    Arguments = "package",
                }
            };
            Thread.Sleep(1000);
            process.Start();

            process.WaitForExit();
            RunJar(projectPath);

        }
    }
}