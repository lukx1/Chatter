using System;
using System.Linq;
using System.Web;
using Newtonsoft.Json;
using System.Collections.Generic;
using ChatterWpf.Models;
using ChatterWpf.Comms;
using System.Net.Http;

public class MessageRepo : AbstractRepo
{
    public MessageRepo(Messenger messenger) : base(messenger)
    {
        
    }

    protected override string getController()
    {
        return "Message";
    }

    public Message[] getMessagesInRoom(int id)
    {
        return messenger.Obtain<Message[]>(
                getController(),
                "GetMessagesInRoom",
                HttpMethod.Post,
                createIdObject(id));
    }

    public Message[] getMessagesInRoomSince(int id, DateTime since)
    {
        return messenger.Obtain<Message[]>(
                getController(),
                "GetMessagesInRoom",
                HttpMethod.Post,
                createCustomObjectWithHeader(
                        new KeyValuePair("ID", id),
                        new KeyValuePair("Since", since)
                ));
    }

    public Message[] getNewMessagesForUser(int id)
    {
        return messenger.Obtain<Message[]>(
                getController(),
                "GetNewMessagesForUser",
                HttpMethod.Post,
                createIdObject(id));
    }

    public void setMessage(Message message)
    {
        messenger.Obtain(getController(), "Message", HttpMethod.Post, createMessageObject(message));
    }

    public bool deleteMessage(int id)
    {
        return messenger.Obtain<bool>(getController(), "Message", HttpMethod.Delete, createIdObject(id));
    }

    public void addMessage(Message message)
    {
        messenger.Obtain(getController(), "Message", HttpMethod.Put, createMessageObject(message));
    }

    private object createMessageObject(Message message)
    {
        return new MessageObject(message,loginHeader.Login,loginHeader.Password);
    }

    public class MessageObject
    {
        public string Login { get; set; }
        public string Password { get; set; }
        public Message Message { get; set; }

        public MessageObject(Message message, string Login, string Password)
        {
            this.Login = Login;
            this.Password = Password;
            this.Message = message;
        }
    }
}
