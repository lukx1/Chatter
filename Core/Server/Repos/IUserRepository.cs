using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public interface IUserRepository
    {
        IEnumerable<Users> GetUsers();
        Users GetUser(int id);
        Users GetUserWithLogin(string login);
        bool RemoveUser(int id);
        void RegisterUser(Users user);
        bool IsLoginValid(string login, string password);
        void SetUser(Users users);
    }

}
