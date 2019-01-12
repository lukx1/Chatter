package night.legacy.org.chatterandroid;

import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class AndroidUser extends User {

    public ArrayList<AndroidUser> Friends;
    public ArrayList<AndroidUser> FriendshipPending;
    public ArrayList<AndroidUser> Blocked;

    public AndroidUser(User user)
    {
        this.firstName = user.firstName;
        this.secondName = user.secondName;
        this.login = user.login;
        this.email = user.email;
        this.password = user.password;
        this.picture = user.picture;
        this.dateLastLogin = user.dateLastLogin;
        this.id = user.id;
        this.dateRegistered = user.dateRegistered;
        this.status = user.status;
    }

    public AndroidUser()
    {

    }

    public void loadRelationships() throws Exception {
        App.getInstance().Connector.loadRelForUser(this);
    }


}
