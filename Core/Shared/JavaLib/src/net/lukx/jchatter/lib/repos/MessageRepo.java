package net.lukx.jchatter.lib.repos;

import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.bind.marshaller.MinimumEscapeHandler;
import com.sun.xml.internal.ws.api.model.MEP;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.CFile;
import net.lukx.jchatter.lib.models.Message;
import net.lukx.jchatter.lib.models.Room;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

public class MessageRepo extends AbstractRepo {

    public MessageRepo(Communicator communicator) {
        super(communicator);
    }

    @Override
    protected String getController() {
        return "Message";
    }

    public Message[] getMessagesInRoom(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "GetMessagesInRoom",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                Message[].class);
    }

    public Message[] getMessagesInRoomSince(int id, Date since) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "GetMessagesInRoom",
                Communicator.HttpMethod.POST,
                createCustomObjectWithHeader(
                        new KeyValuePair("ID",id),
                        new KeyValuePair("Since",since)
                ),
                Message[].class);
    }

    public Message[] getNewMessagesForUser(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "GetNewMessagesForUser",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                Message[].class);
    }

    public void setMessage(Message message) throws IOException, URISyntaxException {
        communicator.Obtain(getController(),"Message", Communicator.HttpMethod.POST,createMessageObject(message),void.class);
    }

    public boolean deleteMessage(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"Message", Communicator.HttpMethod.DELETE,createIdObject(id),boolean.class);
    }

    public void addMessage(Message message) throws IOException, URISyntaxException {
        communicator.Obtain(getController(),"Message", Communicator.HttpMethod.PUT,createMessageObject(message),void.class);
    }

    private Object createMessageObject(@NotNull Message message){
        return new MessageObject(message);
    }

    private class MessageObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private Message Message;

        MessageObject(Message message){
            this.Message = message;
        }
    }
}
