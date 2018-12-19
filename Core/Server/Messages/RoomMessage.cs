using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Messages
{
    public class RoomMessage : LoginHeader
    {
        public Room Room { get; set; }
    }
}
