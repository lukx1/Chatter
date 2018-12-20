using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public interface IMessageRepository
    {
        Task<IEnumerable<Messages>> GetMessagesInRoom(int idRoom);
        Task<IEnumerable<Messages>> GetMessagesInRoomSince(int idRoom, DateTime since);
        Task<IEnumerable<Messages>> GetNewMessagesForUser(int idUser);     
        Task<bool> RemoveMessage(int idMessage);
        Task SetMesssage(Messages message);
        Task AddMessage(Messages message);
    }

}
