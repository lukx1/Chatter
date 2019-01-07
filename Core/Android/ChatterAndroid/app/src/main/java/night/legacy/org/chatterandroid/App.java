package night.legacy.org.chatterandroid;

import net.lukx.jchatter.lib.models.User;

public class App {
    private static final App ourInstance = new App();

    public static App getInstance() {
        return ourInstance;
    }

    private App() {
    }

    public User LoggedUser;
}
