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

        public int id{get;set;}

        public string firstName{get;set;}

        public string secondName{get;set;}

        public string login{get;set;}

        public string password{get;set;}

        /// <summary>
        /// Picture. FK to CFile UUID
        /// </summary>
        public byte[] picture{get;set;}

        public string email{get;set;}

        public DateTime dateRegistered{get;set;}

        public DateTime dateLastLogin{get;set;}

        /// <summary>
        /// EnumSet mapping to @link UserStatus
        /// </summary>
        public UserStatus status{get;set;}
    }
}