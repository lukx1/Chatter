using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Cfiles
    {
        public byte[] Uuid { get; set; }
        public int Iduploader { get; set; }
        public int Idroom { get; set; }
        public string FileName { get; set; }

        public virtual Rooms IdroomNavigation { get; set; }
        public virtual Users IduploaderNavigation { get; set; }
    }
}
