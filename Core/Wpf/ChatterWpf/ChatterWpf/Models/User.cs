using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ChatterWpf.Models
{

    public class User
    {
        public User()
        {
        }

        public int id;

        public string firstName;

        public string secondName;

        public string login;

        public string password;

        /// <summary>
        /// Picture. FK to CFile UUID
        /// </summary>
        public byte[] picture;

        public string email;

        public DateTime dateRegistered;

        public DateTime dateLastLogin;

        /// <summary>
        /// EnumSet mapping to @link UserStatus
        /// </summary>
        public UserStatus status;
    }
}