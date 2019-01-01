package net.lukx.jchatter.lib.repos;

import net.lukx.jchatter.lib.PublicApi;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.Room;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URISyntaxException;

/***
 * Repo for obtaining rooms
 * from the server
 */
@PublicApi
public class RoomRepo extends AbstractRepo {

    /***
     * Creates an instance of this class
     * @param communicator to send requests with
     */
    public RoomRepo(Communicator communicator) {
        super(communicator);
    }

    /***
     * {@inheritDoc}
     */
    @Override
    protected String getController() {
        return "Room";
    }

    /***
     * Gets all rooms a user is in
     * @param id of the user
     * @return rooms
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public Room[] getRoomsWithUser(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "getRoomsWithUser",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                Room[].class);
    }

    /***
     * Gets all users in a room
     * @param id of the room
     * @return users
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public User[] getUsersInRoom(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "GetUsersInRoom",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                User[].class);
    }

    /***
     * Deletes a room. Only the creator can do this
     * @param id of the room
     * @return true if removed, false otherwise
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public boolean removeRoom(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(), "Room", Communicator.HttpMethod.DELETE, createIdObject(id), boolean.class);
    }

    /***
     * Creates a new room
     * @param room to create
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public void addRoom(Room room) throws IOException, URISyntaxException {
        communicator.Obtain(getController(), "Room", Communicator.HttpMethod.PUT, createRoomObject(room), void.class);
    }

    /***
     * Updates information about a room
     * @param room to update
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public void setRoom(Room room) throws IOException, URISyntaxException {
        communicator.Obtain(getController(), "Room", Communicator.HttpMethod.POST, createRoomObject(room), void.class);
    }

    /***
     * Add a user to a room. Can only be done if the user is not in the room already
     * @param idUser to add
     * @param idRoom to add to
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public void addUserToRoom(int idUser, int idRoom) throws IOException, URISyntaxException {
        communicator.Obtain(getController(), "AddUserToRoom", Communicator.HttpMethod.PUT, createIDUserIDRoomObject(idUser, idRoom), void.class);
    }

    /***
     * Removes a user from a room. Only the creator can do this and they can't remove themself. Only not one-on-one rooms allow removing users.
     * @param idUser to remove
     * @param idRoom to remove from
     * @return true if removed, false otherwise
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public boolean removeUserFromRoom(int idUser, int idRoom) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(), "RemoveUserFromRoom", Communicator.HttpMethod.DELETE, createIDUserIDRoomObject(idUser, idRoom), boolean.class);
    }

    private Object createRoomObject(Room room) {
        return new RoomObject(room);
    }

    private Object createIDUserIDRoomObject(int idUser, int idRoom) {
        return new IDUserIDRoomObject(idUser, idRoom);
    }

    @SuppressWarnings("unused")
    private class IDUserIDRoomObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private int IDRoom;
        private int IDUser;

        IDUserIDRoomObject(int idUser, int idRoom) {
            this.IDRoom = idRoom;
            this.IDUser = idUser;
        }
    }

    @SuppressWarnings("unused")
    private class RoomObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private Room Room;

        RoomObject(Room room) {
            this.Room = room;
        }
    }
}
