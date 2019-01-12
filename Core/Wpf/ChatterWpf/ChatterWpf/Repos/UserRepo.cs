using System;
using System.Linq;
using System.Web;
using ChatterWpf.Models;
using ChatterWpf.Comms;
using System.Net.Http;

public class UserRepo : AbstractRepo
{

    public UserRepo(Messenger messenger) : base(messenger)
    {
        
    }

    protected override string getController()
    {
        return "User";
    }

    public User[] getUsers()
    {
        return messenger.Obtain<User[]>(getController(), "GetUsers", HttpMethod.Post, loginHeader);
    }

    public User getUser(int id)
    {
        return messenger.Obtain<User>(getController(), "GetUser", HttpMethod.Post, createIdObject(id));
    }

    public User getUserWithLogin(string login){
        return messenger.Obtain<User>(getController(), "GetUserWithLogin", HttpMethod.Post, new LoginObject(login,loginHeader.Login,loginHeader.Password));
    }

    public void registerUser(User user)
    {
        messenger.Obtain(getController(), "RegisterUser", HttpMethod.Put, createCustomObjectWithHeader(new KeyValuePair("User", user)));
    }

    public bool validateLogin()
    {
        return messenger.Obtain<bool>(getController(), "ValidateLogin", HttpMethod.Post, loginHeader);
    }

    public bool deleteUser(int id)
    {
        return messenger.Obtain<bool>(getController(), "User", HttpMethod.Delete, createIdObject(id));
    }

    public void setUser(User user)
    {
        messenger.Obtain(getController(), "User", HttpMethod.Post, loginHeader);
    }

    public class LoginObject
    {
        public string Login { get; set; }
        public string Password { get; set; }
        public string UserLogin { get; set; }

        public LoginObject(string userLogin, string Login, string Password)
        {
            this.UserLogin = userLogin;
            this.Login = Login;
            this.Password = Password;
        }
    }

}
