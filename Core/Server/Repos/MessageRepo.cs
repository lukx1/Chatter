using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Server.Models;

namespace Server.Repos
{
    public class MessageRepo : BaseRepo,IMessageRepository
    {

        public async Task AddMessage(Messages message)
        {
            await Context.Messages.AddAsync(message);
            await Context.SaveChangesAsync();
        }

        public async Task<IEnumerable<Messages>> GetMessagesInRoom(int idRoom)
        {
            return await Task.Run( () =>
            {
                var m = Context.Messages
                .Where(r => r.IdroomReceiverNavigation.Id == idRoom)
                .ToHashSet();
                return m;
            }
            );
        }

        public async Task<IEnumerable<Messages>> GetMessagesInRoomSince(int idRoom, DateTime since)
        {
            return await Task.Run(() =>
            {
                var m = Context.Messages
                .Where(r => r.IdroomReceiverNavigation.Id == idRoom && r.DateSent > since)
                .ToHashSet();
                return m;
            }
            );
        }

        public async Task<IEnumerable<Messages>> GetNewMessagesForUser(int idUser)
        {
            return await Task.Run(() =>
            {
                return (from users in Context.Users
                        join roomusers in Context.Roomusers on users.Id equals roomusers.Iduser
                        join messages in Context.Messages on roomusers.Idroom equals messages.IdroomReceiver
                        where users.Id == idUser
                        select messages).ToHashSet();
            }
            );
            
        }

        public async Task<bool> RemoveMessage(int idMessage)
        {
            Context.Messages.Remove(Context.Messages.Find(idMessage));
            await Context.SaveChangesAsync();
            return true;
        }

        public async Task SetMesssage(Messages message)
        {
            await Task.Run(() =>
            {
                var msg = Context.Messages.Find(message.Id);
                msg.Content = message.Content;
                msg.DateEdited = message.DateEdited;
                msg.DateRead = message.DateRead;
                msg.DateReceived = message.DateReceived;
                msg.DateSent = message.DateSent;
                msg.Edited = message.Edited;
                msg.IdroomReceiver = message.IdroomReceiver;
                msg.Idsender = message.Idsender;
                msg.Received = message.Received;
                msg.Seen = message.Seen;
                msg.Writing = message.Writing;
                Context.SaveChanges();
            }
            );
        }
    }
}
