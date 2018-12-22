using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;

namespace Server
{
    static class SCCFG
    {
        public const string sccfg = "sql.sccfg";

        public static string GetConnectionString()
        {
            string currentDir = System.IO.Path.GetDirectoryName(Assembly.GetEntryAssembly().Location);
            var best = Path.Combine(currentDir, sccfg);
            var second = Path.Combine(Directory.GetParent(currentDir).Parent.Parent.Parent.FullName, sccfg);
            if (File.Exists(best))
            {
                return File.ReadAllText(best);
            }
            else if (File.Exists(second))
            {
                return File.ReadAllText(second);
            }
            throw new FileNotFoundException("sql.sccfg is required to connect to the database");
        }
    }
}
