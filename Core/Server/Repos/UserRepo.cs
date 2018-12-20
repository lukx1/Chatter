﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Server.Models;

namespace Server.Repos
{
    public class UserRepo : IUserRepository
    {
        private readonly ChatterContext context;

        public UserRepo()
        {
            this.context = new ChatterContext();
        }

        public Users GetUser(int id)
        {
            return context.Users.Find(id);
        }

        public IEnumerable<Users> GetUsers()
        {
            return context.Users;
        }

        public Users GetUserWithLogin(string login)
        {
            return context.Users.FirstOrDefault(r => r.Login == login);
        }

        public bool IsLoginValid(string login, string password)
        {
            //TODO:Add password factory
#if DEBUG
            return true;
#else
            throw new NotImplementedException();
#endif            
        }

        public void RegisterUser(Users user)
        {
            context.Users.Add(user);
            context.SaveChanges();
        }

        public bool RemoveUser(int id)
        {
            var user = context.Users.Find(id);
            if (user == null)
                return false;
            context.Users.Remove(user);
            context.SaveChanges();
            return true;
        }

        public void SetUser(Users u)
        {
            var user = context.Users.Find(u.Id);
            user.FirstName = u.FirstName;
            user.SecondName = u.SecondName;
            if(u.Password != null)
            {
                //TODO:Password factory here
                user.Password = user.Password;
            }
            user.PictureUrl = u.PictureUrl;
            user.Email = u.Email;
            user.DateLastLogin = u.DateLastLogin;
            user.Status = u.Status;
            context.SaveChanges();
        }
    }
}