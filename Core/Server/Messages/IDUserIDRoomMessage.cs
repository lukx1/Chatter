using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Messages
{
    public class IDUserIDRoomMessage : LoginHeader
    {
        public int IDUser { get; set; }
        public int IDRoom { get; set; }
    }
}
