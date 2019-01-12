using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ChatterWpf.Models
{

    public class Message
    {

        public int id;

        //FK to User's ID, ID of user who sent this message.

        public int idsender;

        public int idroomReceiver;

        public string content;

        public bool received;

        public bool seen;

        public bool edited;

        public bool writing;

        public DateTime dateSent;

        public DateTime dateReceived;

        public DateTime dateRead;

        public DateTime dateEdited;

        public Message()
        {

        }
    }

}