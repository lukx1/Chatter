package night.legacy.org.chatterandroid;

import net.lukx.jchatter.lib.models.Message;
import net.lukx.jchatter.lib.models.Room;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class AndroidRoom extends Room {

    public User[] Users;
    public Message[] Messages;

    public AndroidRoom(Room room, User[] users)
    {
        Users = users;
        this.id = room.id;
        this.idcreator = room.idcreator;
        this.name = room.name;
        this.oneOnOne = room.oneOnOne;
        this.picture = room.picture;
        Messages = new Message[0];
        try {
            App.getInstance().Connector.getRoomMessages(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public User getOtherUser(User user)
    {
        for (User item:Users) {
            if(item.id != user.id)
                return item;
        }
        return null;
    }
}
