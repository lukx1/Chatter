using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public interface IRoomRepository 
    {
        IEnumerable<Room> GetRoomsWithUser(int idUser);
        IEnumerable<User> GetUsersInRoom(int id);
        bool RemoveRoom(int id);
        void AddRoom(Room room);
        void SetRoom(Room room);
        void AddUserToRoom(int userId, int idRoom);
        bool RemoveUserFromRoom(int userId, int idRoom);
    }

}
