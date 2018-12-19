using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Server.Messages;
using Server.Models;
using Server.Repos;

namespace Server.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class UserController : ValidatingController
    {
        public IUserRepository UserRepository;

        // GET: api/User
        [HttpPost]
        public IEnumerable<User> GetUsers(LoginHeader header)
        {
            if (IsLoginValid(header))
            {
                return UserRepository.GetUsers();
            }
            return null;
        }

        // GET: api/User/5
        [HttpPost]
        public User GetUserWithLogin(LoginMessage message)
        {
            if (IsLoginValid(message))
            {
                return UserRepository.GetUserWithLogin(message.Login);
            }
            return null;
        }

        // POST: api/User
        [HttpPost]
        public bool RemoveUser(IDMessage message)
        {
            if (IsLoginValid(message))
            {
                return UserRepository.RemoveUser(message.ID);
            }
            return false;
        }

        [HttpPost]
        public void RegisterUser(UserMessage message)
        {
            if (IsLoginValid(message))
            {
                UserRepository.RegisterUser(message.User);
            }
            return;
        }

        // DELETE: api/ApiWithActions/5
        [HttpPost]
        public bool IsLoginValid(LoginHeader message)
        { 
            return UserRepository.IsLoginValid(message.Login,message.Password);
        }

        [HttpPost]
        public void SetUser(UserMessage message)
        {
            if (IsLoginValid(message))
            {
                UserRepository.SetUser(message.User);
            }
            return;
        }

    }
}
