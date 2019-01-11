using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

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

     //Picture. FK to CFile UUID

    public byte[] picture;

    public string email;

    public Datetime dateRegistered;

    public Date dateLastLogin;

     // EnumSet mapping to @link UserStatus

    public int status;
}
