using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.MessageClasses
{
    public class IDRoomMessage : LoginHeader
    {
        public int ID { get; set; }
        public Rooms Room { get; set; }
    }
}
