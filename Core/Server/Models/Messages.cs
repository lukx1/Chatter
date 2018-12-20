using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Messages
    {
        public int Id { get; set; }
        public int Idsender { get; set; }
        public int IdroomReceiver { get; set; }
        public string Content { get; set; }
        public bool Received { get; set; }
        public bool Seen { get; set; }
        public bool Edited { get; set; }
        public bool Writing { get; set; }
        public DateTime DateSent { get; set; }
        public DateTime? DateReceived { get; set; }
        public DateTime? DateRead { get; set; }
        public DateTime? DateEdited { get; set; }

        public virtual Rooms IdroomReceiverNavigation { get; set; }
        public virtual Users IdsenderNavigation { get; set; }
    }
}
