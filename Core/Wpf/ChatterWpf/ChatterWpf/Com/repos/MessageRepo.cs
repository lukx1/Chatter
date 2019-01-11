using System;
using System.Linq;
using System.Web;
using Newtonsoft.Json;
using System.Collections.Generic;
using Wpf.ChatterWpf.ChatterWpf.Models;


public class MessageRepo : AbstractRepo
{
    public MessageRepo(ICommunicable communicable)
    {
        base(communicable);
    }

    protected override string getController()
    {
        return "Message";
    }

    public Message[] getMessagesInRoom(int id)
    {
        return communicable.Obtain(
                getController(),
                "GetMessagesInRoom",
                HttpMethod.POST,
                createIdObject(id),
                typeof(Message[]));
    }

    public Message[] getMessagesInRoomSince(int id, Date since)
    {
        return communicable.Obtain(
                getController(),
                "GetMessagesInRoom",
                HttpMethod.POST,
                createCustomObjectWithHeader(
                        new KeyValuePair("ID", id),
                        new KeyValuePair("Since", since)
                ),
                typeof(Message[]));
    }

    public Message[] getNewMessagesForUser(int id)
    {
        return communicable.Obtain(
                getController(),
                "GetNewMessagesForUser",
                HttpMethod.POST,
                createIdObject(id),
                typeof(Message[]));
    }

    public void setMessage(Message message)
    {
        communicable.Obtain(getController(), "Message", HttpMethod.POST, createMessageObject(message), typeof(void));
    }

    public bool deleteMessage(int id)
    {
        return communicable.Obtain(getController(), "Message", HttpMethod.DELETE, createIdObject(id), typeof(bool));
    }

    public void addMessage(Message message)
    {
        communicable.Obtain(getController(), "Message", HttpMethod.PUT, createMessageObject(message), typeof(void));
    }

    private Object createMessageObject(Message message)
    {
        return new MessageObject(message);
    }

    private class MessageObject
    {
        private string Login = getLoginHeader().getLogin();
        private string Password = getLoginHeader().getPassword();
        private Message Message;

        MessageObject(Message message)
        {
            this.Message = message;
        }
    }
}
