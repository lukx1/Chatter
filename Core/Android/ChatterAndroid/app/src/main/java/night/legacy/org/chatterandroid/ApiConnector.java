package night.legacy.org.chatterandroid;

import net.lukx.jchatter.lib.comms.Communicable;
import net.lukx.jchatter.lib.repos.UserRepo;

import java.net.URI;
import java.net.URISyntaxException;

public class ApiConnector {
    private AndroidConnection connection;
    public UserRepo userRepo;

    public ApiConnector() throws URISyntaxException {
        connection = new AndroidConnection();
        connection.setServerURI(new URI("http://78.102.218.164:8080/api"));
        userRepo = new UserRepo(connection);
    }


}
