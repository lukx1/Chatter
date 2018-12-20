using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.MessageClasses
{
    public class IDSinceMessage : LoginHeader
    {
        public int ID { get; set; }
        public DateTime Since { get; set; }
    }
}
