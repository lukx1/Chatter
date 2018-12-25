package net.lukx.jchatter.lib.repos;

import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.NotNull;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

public class UserRepo extends AbstractRepo{

    @Override
    protected String getController() {
        return "User";
    }

    public UserRepo(net.lukx.jchatter.lib.comms.Communicator communicator){
        super(communicator);
    }

    public User[] getUsers() throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"GetUsers", Communicator.HttpMethod.POST, getLoginHeader(),User[].class);
    }

    public User getUser(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"GetUser", Communicator.HttpMethod.POST,createIdObject(id),User.class);
    }

    public User getUserWithLogin(@NotNull String login) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"GetUserWithLogin", Communicator.HttpMethod.POST,new LoginObject(login),User.class);
    }

    public  void registerUser(@NotNull User user) throws IOException, URISyntaxException {
        communicator.Obtain(getController(),"RegisterUser", Communicator.HttpMethod.PUT,user,Void.class);
    }

    public boolean validateLogin() throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"ValidateLogin", Communicator.HttpMethod.POST,getLoginHeader(),boolean.class);
    }

    public boolean deleteUser(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"User", Communicator.HttpMethod.DELETE,createIdObject(id),boolean.class);
    }

    public void setUser(User user) throws IOException, URISyntaxException {
        communicator.Obtain(getController(),"User", Communicator.HttpMethod.POST,getLoginHeader(),Void.class);
    }

    private class LoginObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private String UserLogin;

        LoginObject(String userLogin){
            this.UserLogin = userLogin;
        }
    }
}
