using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Messages
{
    public class LoginMessage : LoginHeader
    {
        public string Login { get; set; }
    }
}
