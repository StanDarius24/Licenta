
namespace Interpreter.Utility
{
    public static class Reader
    {
        public static string ReadFromPath(string path)
        {
            return System.IO.File.ReadAllText(path);
        }
    }
}