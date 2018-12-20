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
            Roomusers = new HashSet<Roomusers>();
        }

        public int Id { get; set; }
        public int Idcreator { get; set; }
        public string Name { get; set; }
        public bool OneOnOne { get; set; }

        public virtual Users IdcreatorNavigation { get; set; }
        public virtual ICollection<Cfiles> Cfiles { get; set; }
        public virtual ICollection<Messages> Messages { get; set; }
        public virtual ICollection<Roomusers> Roomusers { get; set; }
    }
}
