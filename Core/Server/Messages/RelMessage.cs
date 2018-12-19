using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Messages
{
    public class RelMessage : LoginHeader
    {
        public Relationship Relationship { get; set; }
    }
}
