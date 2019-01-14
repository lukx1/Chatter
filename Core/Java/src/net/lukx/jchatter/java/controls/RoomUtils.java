package net.lukx.jchatter.java.controls;

import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.Room;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class RoomUtils {

    public static User getOtherUserInRoom(Repos repos, Room room, User currentUser) throws IOException, URISyntaxException {
        if(!room.oneOnOne)
            throw new IllegalArgumentException("Room must be 1 on 1");

        User[] users = repos.getRoomRepo().getUsersInRoom(room.id);
        return users[0].id == currentUser.id ? users[1] : users[0];
    }

}
