package night.legacy.org.chatterandroid;

import net.lukx.jchatter.lib.comms.Communicable;
import net.lukx.jchatter.lib.models.Relationship;
import net.lukx.jchatter.lib.models.RelationshipStatus;
import net.lukx.jchatter.lib.models.Room;
import net.lukx.jchatter.lib.models.User;
import net.lukx.jchatter.lib.repos.MessageRepo;
import net.lukx.jchatter.lib.repos.RelationshipRepo;
import net.lukx.jchatter.lib.repos.RoomRepo;
import net.lukx.jchatter.lib.repos.UserRepo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ApiConnector {
    private AndroidConnection connection;
    public UserRepo userRepo;
    public RelationshipRepo relRepo;
    public RoomRepo roomRepo;
    public MessageRepo messageRepo;
    private User[]  Users;


    public ApiConnector() throws URISyntaxException {
        connection = new AndroidConnection();
        connection.setServerURI(new URI("http://78.102.218.164:8080/api"));
        userRepo = new UserRepo(connection);
        relRepo = new RelationshipRepo(connection);
        roomRepo = new RoomRepo(connection);
        messageRepo = new MessageRepo(connection);
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
    public User[] getUsers() {return Users;}

    public void loadRelForUser(AndroidUser user) throws IOException, URISyntaxException {
         Relationship[] rels = relRepo.getRelForUser(user.id);
        for (Relationship item : rels) {
            if(item.relationType == RelationshipStatus.FRIEND.getKey())
            {
                if(item.idsourceUser == user.id)
                    user.Friends.add(new AndroidUser(getUser(item.idtargetUser)));
                else
                    user.Friends.add(new AndroidUser(getUser(item.idsourceUser)));
            }
            if(item.relationType == RelationshipStatus.FRIENDSHIP_PENDING.getKey())
            {
                if(item.idtargetUser == user.id)
                    user.Friends.add(new AndroidUser(getUser(item.idsourceUser)));
            }
            if(item.relationType == RelationshipStatus.BLOCKED.getKey())
            {
                if(item.idsourceUser == user.id)
                    user.Blocked.add(new AndroidUser(getUser(item.idtargetUser)));
            }
        }
    }

    public void getRoomsForUser(AndroidUser user) throws IOException, URISyntaxException {
        Room[] rooms = roomRepo.getRoomsWithUser(user.id);
        for (Room item:rooms) {
            user.Rooms.add(new AndroidRoom(item,roomRepo.getUsersInRoom(item.id)));
        }
    }

    public void getRoomMessages(AndroidRoom room) throws IOException, URISyntaxException {
        room.Messages = messageRepo.getMessagesInRoom(room.id);
    }
}
