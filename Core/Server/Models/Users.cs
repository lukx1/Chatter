﻿using System;
using System.Collections.Generic;

namespace Server.Models
{
    public partial class Users
    {
        public Users()
        {
            Cfiles = new HashSet<Cfiles>();
            Messages = new HashSet<Messages>();
            RelationshipsIdsourceUserNavigation = new HashSet<Relationships>();
            RelationshipsIdtargetUserNavigation = new HashSet<Relationships>();
        }

        public int Id { get; set; }
        public string FirstName { get; set; }
        public string SecondName { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public string PictureUrl { get; set; }
        public string Email { get; set; }
        public DateTime DateRegistered { get; set; }
        public DateTime DateLastLogin { get; set; }
        public int Status { get; set; }

        public virtual ICollection<Cfiles> Cfiles { get; set; }
        public virtual ICollection<Messages> Messages { get; set; }
        public virtual ICollection<Relationships> RelationshipsIdsourceUserNavigation { get; set; }
        public virtual ICollection<Relationships> RelationshipsIdtargetUserNavigation { get; set; }
    }
}