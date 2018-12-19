using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Messages
{
    public class IDMessage : LoginHeader
    {
        public int ID { get; set; }
    }
}
