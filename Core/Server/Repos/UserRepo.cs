using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Server.Models;
using System.Text;
using Server.Helpers;

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
            var usr = context.Users.FirstOrDefault(x => x.Login == login);
            if (usr == null)
            {
                return false;
            }
            var result = PasswordHelper.VerifyPasswordPbkdf2(password, usr.Password);
            if (result)
            {
                usr.DateLastLogin = DateTime.Now;
                context.SaveChanges();
            }
            return result;
        }

        public void RegisterUser(Users user)
        {
            user.Password = PasswordHelper.HashPasswordPbkdf2(Encoding.ASCII.GetString(user.Password)).AsBytes();
            context.Users.Add(user);
            context.SaveChanges();
        }

        private IQueryable<Roomusers> GetAllRoomUsersWithUser(Users users)
        {
            return context.Roomusers.Where(r => r.Iduser == users.Id);
        }

        private void RemoveAllUsersRoom(Users users)
        {
            foreach (var roomse in new RepoHelper(context).GetAllOneOnOneRoomsWithUser(users.Id))
            {
                context.Rooms.Remove(roomse);
            }
            foreach (var roomusers in GetAllRoomUsersWithUser(users))
            {
                context.Roomusers.Remove(roomusers);
            }

            context.SaveChanges();
        }

        public bool RemoveUser(int id)
        {
            var user = context.Users.Find(id);
            if (user == null)
                return false;
            foreach (var item in context.Cfiles)
            {
                if (item.Iduploader == id)
                {
                    context.Cfiles.Remove(item);
                }
            }
            RemoveAllUsersRoom(user);
            context.Users.Remove(user);
            context.SaveChanges();
            return true;
        }

        public void SetUser(Users u)
        {
            var user = context.Users.Find(u.Id);
            user.FirstName = u.FirstName;
            user.SecondName = u.SecondName;
            if (u.Password != null && u.Password.Length != 0 && u.Password.Length < 32)
            {
                user.Password = PasswordHelper.HashPasswordPbkdf2(Encoding.UTF8.GetString(u.Password)).AsBytes();
            }
            user.Picture = u.Picture;
            user.Email = u.Email;
            user.DateLastLogin = u.DateLastLogin;
            user.Status = u.Status;
            context.SaveChanges();
        }
    }
}
