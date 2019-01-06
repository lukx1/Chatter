package net.lukx.jchatter.lib.repos;

import com.sun.istack.internal.NotNull;
import net.lukx.jchatter.lib.PublicApi;
import net.lukx.jchatter.lib.comms.Communicable;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.Message;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

/***
 * Repo for obtaining messages
 * from the server
 */
@PublicApi
public class MessageRepo extends AbstractRepo {

    /***
     * Creates an instance of this class
     * @param communicable to send requests with
     */
    public MessageRepo(Communicable communicable) {
        super(communicable);
    }

    /***
     * {@inheritDoc}
     */
    @Override
    protected String getController() {
        return "Message";
    }

    /***
     * Gets all messages that have been sent in a room
     * @param id of the room
     * @return messages
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public Message[] getMessagesInRoom(int id) throws IOException, URISyntaxException {
        return communicable.Obtain(
                getController(),
                "GetMessagesInRoom",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                Message[].class);
    }

    /***
     * Gets all messages that have been sent in a room since a specified date
     * @param id of the room
     * @param since date
     * @return messages
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public Message[] getMessagesInRoomSince(int id, Date since) throws IOException, URISyntaxException {
        return communicable.Obtain(
                getController(),
                "GetMessagesInRoom",
                Communicator.HttpMethod.POST,
                createCustomObjectWithHeader(
                        new KeyValuePair("ID",id),
                        new KeyValuePair("Since",since)
                ),
                Message[].class);
    }

    /***
     * Gets all messages that have been sent to a since he was last online
     * @param id of the user
     * @return messages
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public Message[] getNewMessagesForUser(int id) throws IOException, URISyntaxException {
        return communicable.Obtain(
                getController(),
                "GetNewMessagesForUser",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                Message[].class);
    }

    /***
     * Sets a message
     * @param message to set
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public void setMessage(Message message) throws IOException, URISyntaxException {
        communicable.Obtain(getController(),"Message", Communicator.HttpMethod.POST,createMessageObject(message),void.class);
    }

    /***
     * Removes a message
     * @param id of the message
     * @return true if removed, false otherwise
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public boolean deleteMessage(int id) throws IOException, URISyntaxException {
        return communicable.Obtain(getController(),"Message", Communicator.HttpMethod.DELETE,createIdObject(id),boolean.class);
    }

    /***
     * Adds a message
     * @param message to add
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public void addMessage(Message message) throws IOException, URISyntaxException {
        communicable.Obtain(getController(),"Message", Communicator.HttpMethod.PUT,createMessageObject(message),void.class);
    }

    private Object createMessageObject(@NotNull Message message){
        return new MessageObject(message);
    }



    @SuppressWarnings("unused")
    private class MessageObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private Message Message;

        MessageObject(Message message){
            this.Message = message;
        }
    }
}
