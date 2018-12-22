package net.lukx.jchatter.lib.repos;

import com.google.gson.reflect.TypeToken;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.Room;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class RoomRepo extends AbstractRepo {

    public RoomRepo(Communicator communicator) {
        super(communicator);
    }

    @Override
    protected String getController() {
        return "Room";
    }

    public Iterable<Room> getRoomsWithUser(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "getRoomsWithUser",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                new TypeToken<Iterable<Room>>() {}.getType());
    }

    public Iterable<User> getUsersInRoom(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "GetUsersInRoom",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                new TypeToken<Iterable<User>>() {}.getType());
    }

    public boolean removeRoom(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"Room", Communicator.HttpMethod.DELETE,createIdObject(id),boolean.class);
    }

    public void addRoom(Room room) throws IOException, URISyntaxException {
        communicator.Obtain(getController(),"Room", Communicator.HttpMethod.PUT,createRoomObject(room),void.class);
    }

    public void setRoom(Room room) throws IOException, URISyntaxException {
        communicator.Obtain(getController(),"Room", Communicator.HttpMethod.POST,createRoomObject(room),void.class);
    }

    public void addUserToRoom(int idUser,int idRoom) throws IOException, URISyntaxException {
        communicator.Obtain(getController(),"AddUserToRoom", Communicator.HttpMethod.PUT,createIDUserIDRoomObject(idUser,idRoom),void.class);
    }

    public boolean removeUserFromRoom(int idUser, int idRoom) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"RemoveUserFromRoom", Communicator.HttpMethod.DELETE,createIDUserIDRoomObject(idUser,idRoom),boolean.class);
    }

    private Object createRoomObject(Room room){
        return new RoomObject(room);
    }

    private Object createIDUserIDRoomObject(int idUser, int idRoom){
        return new IDUserIDRoomObject(idUser,idRoom);
    }

    private class IDUserIDRoomObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private int IDRoom;
        private int IDUser;

        IDUserIDRoomObject(int idUser,int idRoom){
            this.IDRoom = idRoom;
            this.IDUser = idUser;
        }
    }

    private class RoomObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private Room Room;

        RoomObject(Room room){
            this.Room = room;
        }
    }
}
