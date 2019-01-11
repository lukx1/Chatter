using System;
using System.Linq;
using System.Web;

public class RoomRepo
{
    public RoomRepo(Communicable communicable)
    {
        base(communicable);
    }

    protected override string getController()
    {
        return "Room";
    }

    public Room[] getRoomsWithUser(int id)
    {
        return communicable.Obtain(
                getController(),
                "getRoomsWithUser",
                HttpMethod.POST,
                createIdObject(id),
                typeof(Room[]));
    }

    public User[] getUsersInRoom(int id)
    {
        return communicable.Obtain(
                getController(),
                "GetUsersInRoom",
                HttpMethod.POST,
                createIdObject(id),
                typeof(User[]));
    }

    public bool removeRoom(int id)
    {
        return communicable.Obtain(getController(), "Room", HttpMethod.DELETE, createIdObject(id), typeof(bool));
    }

    public void addRoom(Room room)
    {
        communicable.Obtain(getController(), "Room", HttpMethod.PUT, createRoomObject(room), typeof(void));
    }

    public void setRoom(Room room)
    {
        communicable.Obtain(getController(), "Room", HttpMethod.POST, createRoomObject(room), typeof(void));
    }

    public void addUserToRoom(int idUser, int idRoom)
    {
        communicable.Obtain(getController(), "AddUserToRoom", HttpMethod.PUT, createIDUserIDRoomObject(idUser, idRoom), typeof(void));
    }

    public bool removeUserFromRoom(int idUser, int idRoom)
    {
        return communicable.Obtain(getController(), "RemoveUserFromRoom", HttpMethod.DELETE, createIDUserIDRoomObject(idUser, idRoom), typeof(bool));
    }

    private Object createRoomObject(Room room)
    {
    return new RoomObject(room);
    }

    private Object createIDUserIDRoomObject(int idUser, int idRoom)
    {
    return new IDUserIDRoomObject(idUser, idRoom);
    }

    private class IDUserIDRoomObject
    {
        private string Login = getLoginHeader().getLogin();
        private string Password = getLoginHeader().getPassword();
        private int IDRoom;
        private int IDUser;

        IDUserIDRoomObject(int idUser, int idRoom)
        {
            this.IDRoom = idRoom;
            this.IDUser = idUser;
        }
    }

    private class RoomObject
    {
        private string Login = getLoginHeader().getLogin();
        private string Password = getLoginHeader().getPassword();
        private Room Room;

        RoomObject(Room room)
        {
            this.Room = room;
        }
    }



}
