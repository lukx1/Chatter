package net.lukx.jchatter.lib.models;

import net.lukx.jchatter.lib.PublicApi;

import java.util.Date;

/***
 * Class used for deserialization of server data
 */
@SuppressWarnings("unused")
@PublicApi
public class Message {
    /**
     * ID
     */
    public int id;
    /***
     * ID of user who sent this message.
     * FK to User's ID
     */
    public int idsender;
    /***
     * Id of room that this was sent to.
     * FK to Room's ID
     */
    public int idroomReceiver;
    /***
     * Content
     */
    public String content;
    /***
     * If message has been received by anyone
     */
    public boolean received;
    /***
     * If message has been seen by anyone
     */
    public boolean seen;
    /***
     * If message ever was edited
     */
    public boolean edited;
    /***
     * If the user is currently writing this message
     */
    public boolean writing;
    /***
     * When this message was sent
     */
    public Date dateSent;
    /***
     * When this message was received
     */
    public Date dateReceived;
    /***
     * When this message was first read
     */
    public Date dateRead;
    /***
     * When this message was last edited
     */
    public Date dateEdited;

    public Message(){

    }

}
