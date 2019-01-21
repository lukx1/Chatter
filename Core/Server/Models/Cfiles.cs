using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Cfiles
    {
        public Cfiles()
        {
            Users = new HashSet<Users>();
        }

        public byte[] Uuid { get; set; }
        public int Iduploader { get; set; }
        public int Idroom { get; set; }
        public string FileName { get; set; }
        public DateTime DateUploaded { get; set; }
        public bool Expired { get; set; }

        public virtual Users IduploaderNavigation { get; set; }
        public virtual ICollection<Users> Users { get; set; }
    }
}
