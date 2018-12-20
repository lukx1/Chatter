using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Relationships
    {
        public int Id { get; set; }
        public int IdsourceUser { get; set; }
        public int IdtargetUser { get; set; }
        public int RelationType { get; set; }
        public DateTime DateCreated { get; set; }

        public virtual Users IdsourceUserNavigation { get; set; }
        public virtual Users IdtargetUserNavigation { get; set; }
    }
}
