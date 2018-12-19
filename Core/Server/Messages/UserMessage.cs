using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Messages
{
    public class UserMessage : LoginHeader
    {
        public User User { get; set; }
    }
}
