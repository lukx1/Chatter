using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Server.Models;

namespace Server.MessageClasses
{
    public class LikeUser
    {
        public int Id { get; set; }
        public string FirstName { get; set; }
        public string SecondName { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public byte[] Picture { get; set; }
        public string Email { get; set; }
        public DateTime DateRegistered { get; set; }
        public DateTime DateLastLogin { get; set; }
        public int Status { get; set; }

        public Users ToUsers()
        {
            return new Users()
            {
                Id = Id,
                FirstName = FirstName,
                SecondName = SecondName,
                Login = Login,
                Password = Password == null ? null : Encoding.UTF8.GetBytes(Password),
                Picture = Picture,
                Email = Email,
                DateRegistered = DateRegistered,
                DateLastLogin = DateLastLogin,
                Status = Status
            };
        }
    }
}
