using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Messages
{
    public class MessageMessage : LoginHeader
    {
        public Message Message { get; set; }
    }
}
