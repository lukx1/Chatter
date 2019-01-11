package night.legacy.org.chatterandroid;

import net.lukx.jchatter.lib.comms.Communicable;
import net.lukx.jchatter.lib.models.User;
import net.lukx.jchatter.lib.repos.UserRepo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ApiConnector {
    private AndroidConnection connection;
    public UserRepo userRepo;
    private User[]  Users;


    public ApiConnector() throws URISyntaxException {
        connection = new AndroidConnection();
        connection.setServerURI(new URI("http://78.102.218.164:8080/api"));
        userRepo = new UserRepo(connection);
    }

    public void loadAllUsers() throws Exception {
        Users = userRepo.getUsers();
        if(Users == null)
        {
            throw new Exception("No users loaded");
        }
        int x = 4;
    }

    public User getUser(int id)
    {
        return Users[id - 1];
    }


}
