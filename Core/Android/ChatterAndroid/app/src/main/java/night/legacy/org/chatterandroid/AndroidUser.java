package night.legacy.org.chatterandroid;

import net.lukx.jchatter.lib.models.User;

import java.util.List;

public class AndroidUser extends User {

    public List<AndroidUser> Friends;

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


}
