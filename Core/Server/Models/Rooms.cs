using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Rooms
    {
        public Rooms()
        {
            Cfiles = new HashSet<Cfiles>();
            Messages = new HashSet<Messages>();
        }

        public int Id { get; set; }
        public string Name { get; set; }
        public bool OneOnOne { get; set; }

        public virtual ICollection<Cfiles> Cfiles { get; set; }
        public virtual ICollection<Messages> Messages { get; set; }
    }
}
