using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public interface IMessageRepository
    {
        IEnumerable<Message> GetMessagesInRoom(int idRoom);
        IEnumerable<Message> GetMessagesInRoomSince(int idRoom, DateTime since);    
        IEnumerable<Message> GetNewMessagesForUser(int idUser);     
        bool RemoveMessage(int idMessage);
        void SetMesssage(Message message);
        void AddMessage(Message message);
    }

}
