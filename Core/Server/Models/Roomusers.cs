using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Roomusers
    {
        public int Id { get; set; }
        public int Iduser { get; set; }
        public int Idroom { get; set; }
    }
}
