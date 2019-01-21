using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.MessageClasses
{
    public class UUIDMessage : LoginHeader
    {
        public string UUID { get; set; }
    }
}
