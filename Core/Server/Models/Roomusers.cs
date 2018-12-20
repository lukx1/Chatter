using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Roomusers
    {
        public int Id { get; set; }
        public int Iduser { get; set; }
        public int Idroom { get; set; }

        public virtual Rooms IdroomNavigation { get; set; }
        public virtual Users IduserNavigation { get; set; }
    }
}
