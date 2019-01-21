using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ChatterWpf.Models
{

    public class Message
    {

        public int id{get;set;}

        //FK to User's ID, ID of user who sent this message.

        public int idsender{get;set;}

        public int idroomReceiver{get;set;}

        public string content{get;set;}

        public bool received{get;set;}

        public bool seen{get;set;}

        public bool edited{get;set;}

        public bool writing{get;set;}

        public DateTime? dateSent{get;set;}

        public DateTime? dateReceived{get;set;}

        public DateTime? dateRead{get;set;}

        public DateTime? dateEdited{get;set;}

        public Message()
        {

        }
    }

}