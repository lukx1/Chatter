using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Server.Models;

namespace Server.Repos
{
    public class RoomRepo : BaseRepo,IRoomRepository
    {

        public void AddRoom(Rooms room)
        {
            Context.Rooms.Add(room);
            Context.SaveChanges();
        }

        public void AddUserToRoom(int idUser, int idRoom)
        {
            var room = Context.Roomusers.FirstOrDefault(r => r.Iduser == idUser && r.Idroom == idRoom);

            if (room != null)
            {
                throw new Exception("User already is in this room");
            }
            if(Context.Rooms.Find(idRoom) == null)
            {
                throw new Exception("Room doesn't exist");
            }
            if(Context.Users.Find(idUser) == null)
            {
                throw new Exception("User doens't exist");
            }

            Context.Roomusers.Add(new Roomusers() { Idroom = idRoom, Iduser = idUser });
            Context.SaveChanges();
        }

        public IEnumerable<Rooms> GetRoomsWithUser(int idUser)
        {
            return (from r in Context.Rooms
                   join ru in Context.Roomusers on r.Id equals ru.Idroom
                   join u in Context.Users on ru.Iduser equals u.Id
                   where ru.Iduser == idUser
                   select r).Distinct();
        }

        public IEnumerable<Users> GetUsersInRoom(int id)
        {
            return from r in Context.Rooms
                   join ru in Context.Roomusers on r.Id equals ru.Idroom
                   join u in Context.Users on ru.Iduser equals u.Id
                   where r.Id == id
                   select u;
        }

        public bool RemoveRoom(int id)
        {
            var r = Context.Rooms.Find(id);
            if(r == null)
            {
                return false;
            }
            foreach (var item in Context.Cfiles)
            {
                if(item.Idroom == id)
                {
                    Context.Cfiles.Remove(item);
                }
            }
            Context.Rooms.Remove(r);
            Context.SaveChanges();
            return true;
        }

        public bool RemoveUserFromRoom(int userId, int idRoom)
        {
            var ru = Context.Roomusers.SingleOrDefault(r => r.Iduser == userId && r.Idroom == idRoom);
            if(ru == null)
            {
                return false;
            }
            Context.Roomusers.Remove(ru);
            Context.SaveChanges();
            return true;
        }

        public void SetRoom(Rooms room)
        {
            var r = Context.Rooms.Find(room.Id);
            r.Idcreator = room.Idcreator;
            r.Name = room.Name;
            r.OneOnOne = room.OneOnOne;
            Context.SaveChanges();
        }
    }
}
