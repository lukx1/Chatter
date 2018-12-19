using Server.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Server.Repos
{
    public interface IUserRepository
    {
        IEnumerable<User> GetUsers();
        User GetUser(int id);
        User GetUserWithLogin(string login);
        bool RemoveUser(int id);
        void RegisterUser(User user);
        bool IsLoginValid(string login, string password);
        void SetUser(User users);
    }

}
