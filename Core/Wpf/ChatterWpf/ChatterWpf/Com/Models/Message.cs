using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

public class Message
{

    public int id;
 
     //FK to User's ID, ID of user who sent this message.

    public int idsender;

    public int idroomReceiver;

    public string content;

    public boolean received;

    public boolean seen;

    public boolean edited;

    public boolean writing;

    public Date dateSent;

    public Date dateReceived;

    public Date dateRead;

    public Date dateEdited;

    public Message()
    {

    }
}
    
