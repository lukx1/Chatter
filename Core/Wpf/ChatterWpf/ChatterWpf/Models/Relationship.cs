﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ChatterWpf.Models
{

    public class Relationship
    {

        public int id;

        // FK to user's ID, ID of user who created this relationship.
        public int idsourceUser;

        // FK to user's ID, ID of user to whom this applies.
        public int idtargetUser;

        //Type of relationship mapping to @link RelationshipStatus
        public RelStatus relationType;

        public DateTime dateCreated;

    }
}