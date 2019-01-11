using System;
using System.String;

//HELP
public class LoginHeader
{

    private string Login;
    private string Password;

    public string getLogin()
    {
        return Login;
    }

    [PublicApi]
    public void setLogin(/*String.IsNullOrEmpty*/string login)
    {
        Login = login;
    }

    [PublicApi]
    public string getPassword()
    {
        return Password;
    }

    [PublicApi]
    public void setPassword(/*@NotNull */string password)
    {
        Password = password;
    }
}
