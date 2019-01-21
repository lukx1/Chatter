using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ChatterWpf.Models
{

    public class Relationship
    {

        public int id{get;set;}

        // FK to user's ID, ID of user who created this relationship.
        public int idsourceUser{get;set;}

        // FK to user's ID, ID of user to whom this applies.
        public int idtargetUser{get;set;}

        //Type of relationship mapping to @link RelationshipStatus
        public RelStatus relationType{get;set;}

        public DateTime dateCreated{get;set;}

    }
}