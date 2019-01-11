using System;
using System.Linq;
using System.Web;

public class UserRepo : AbstractRepo
{

    public UserRepo(Communicable communicable)
    {
        base(communicable);
    }

    protected override string getController()
    {
        return "User";
    }

    public User[] getUsers()
    {
        return communicable.Obtain(getController(), "GetUsers", HttpMethod.POST, getLoginHeader(), typeof(User[]));
    }

    public User getUser(int id)
    {
        return communicable.Obtain(getController(), "GetUser", HttpMethod.POST, createIdObject(id), typeof(User));
    }

    public User getUserWithLogin(string login){
        return communicable.Obtain(getController(), "GetUserWithLogin", HttpMethod.POST, new LoginObject(login), typeof(User));
    }

    public void registerUser(User user)
    {
        communicable.Obtain(getController(), "RegisterUser", HttpMethod.PUT, createCustomObjectWithHeader(new KeyValuePair("User", user)), typeof(void));
    }

    public bool validateLogin()
    {
        return communicable.Obtain(getController(), "ValidateLogin", HttpMethod.POST, getLoginHeader(), typeof(bool));
    }

    public bool deleteUser(int id)
    {
        return communicable.Obtain(getController(), "User", HttpMethod.DELETE, createIdObject(id), typeof(bool));
    }

    public void setUser(User user)
    {
        communicable.Obtain(getController(), "User", HttpMethod.POST, getLoginHeader(), typeof(void));
    }

    private class LoginObject
    {
        private string Login = getLoginHeader().getLogin();
        private string Password = getLoginHeader().getPassword();
        private string UserLogin;

        LoginObject(string userLogin)
        {
            this.UserLogin = userLogin;
        }
    }

}
