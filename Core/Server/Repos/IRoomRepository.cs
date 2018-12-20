using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public interface IRoomRepository 
    {
        IEnumerable<Rooms> GetRoomsWithUser(int idUser);
        IEnumerable<Users> GetUsersInRoom(int id);
        bool RemoveRoom(int id);
        void AddRoom(Rooms room);
        void SetRoom(Rooms room);
        void AddUserToRoom(int userId, int idRoom);
        bool RemoveUserFromRoom(int userId, int idRoom);
    }

}
