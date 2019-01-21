using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ChatterWpf.Models
{

    public class Room
    {
        public int id{get;set;}

        public int idcreator{get;set;}

        public string name{get;set;}

        public bool oneOnOne{get;set;} //If this is only one on one 

        public byte[] picture{get;set;} // FK to CFile UUID

        public Room()
        {

        }

    }
}