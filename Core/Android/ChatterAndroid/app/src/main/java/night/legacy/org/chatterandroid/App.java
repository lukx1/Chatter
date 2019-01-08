package night.legacy.org.chatterandroid;

import net.lukx.jchatter.lib.models.User;

import java.net.URISyntaxException;

public class App {
    private static App ourInstance = null;

    static {
        try {
            ourInstance = new App();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static App getInstance() {
        return ourInstance;
    }

    private App() throws URISyntaxException {
    }

    public User LoggedUser;
    public ApiConnector Connector = new ApiConnector();
}
