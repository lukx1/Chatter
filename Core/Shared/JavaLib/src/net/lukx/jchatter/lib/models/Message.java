package net.lukx.jchatter.lib.models;

import java.util.Date;

public class Message {
    public int id;
    public int idsender;
    public int idroomReceiver;
    public String content;
    public boolean received;
    public boolean seen;
    public boolean edited;
    public boolean writing;
    public Date dateSent;
    public Date dateReceived;
    public Date dateRead;
    public Date dateEdited;

    public Message(){

    }

}
