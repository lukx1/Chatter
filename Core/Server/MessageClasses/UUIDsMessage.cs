using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.MessageClasses
{
    public class UUIDsMessage : LoginHeader
    {
        public IEnumerable<byte[]> UUIDs { get; set; }
    }
}
