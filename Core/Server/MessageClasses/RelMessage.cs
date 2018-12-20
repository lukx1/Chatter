using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.MessageClasses
{
    public class RelMessage : LoginHeader
    {
        public Relationships Relationship { get; set; }
    }
}
