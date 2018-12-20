using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Server.Models;

namespace Server.Repos
{
    public class MessageRepo : IMessageRepository
    {
        private readonly ChatterContext context;

        public MessageRepo(ChatterContext context)
        {
            this.context = context;
        }

        public async Task AddMessage(Messages message)
        {
            await context.Messages.AddAsync(message);
            await context.SaveChangesAsync();
        }

        public async Task<IEnumerable<Messages>> GetMessagesInRoom(int idRoom)
        {
            var m = context.Messages.Where(r => r.IdroomReceiverNavigation.Id == idRoom);
            return m;
        }

        public async Task<IEnumerable<Messages>> GetMessagesInRoomSince(int idRoom, DateTime since)
        {
            throw new NotImplementedException();
        }

        public async Task<IEnumerable<Messages>> GetNewMessagesForUser(int idUser)
        {
            throw new NotImplementedException();
        }

        public async Task<bool> RemoveMessage(int idMessage)
        {
            throw new NotImplementedException();
        }

        public async Task SetMesssage(Messages message)
        {
            throw new NotImplementedException();
        }
    }
}
