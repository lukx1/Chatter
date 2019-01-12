using System;
using System.Linq;
using System.Web;
using ChatterWpf.Models;
using ChatterWpf.Comms;
using System.Net.Http;

public class RoomRepo : AbstractRepo
{
    public RoomRepo(Messenger messenger) : base(messenger)
    {

    }

    protected override string getController()
    {
        return "Room";
    }

    public Room[] getRoomsWithUser(int id)
    {
        return messenger.Obtain<Room[]>(
                getController(),
                "getRoomsWithUser",
                HttpMethod.Post,
                createIdObject(id));
    }

    public User[] getUsersInRoom(int id)
    {
        return messenger.Obtain<User[]>(
                getController(),
                "GetUsersInRoom",
                HttpMethod.Post,
                createIdObject(id));
    }

    public bool removeRoom(int id)
    {
        return messenger.Obtain<bool>(getController(), "Room", HttpMethod.Delete, createIdObject(id));
    }

    public void addRoom(Room room)
    {
        messenger.Obtain(getController(), "Room", HttpMethod.Put, createRoomObject(room));
    }

    public void setRoom(Room room)
    {
        messenger.Obtain(getController(), "Room", HttpMethod.Post, createRoomObject(room));
    }

    public void addUserToRoom(int idUser, int idRoom)
    {
        messenger.Obtain(getController(), "AddUserToRoom", HttpMethod.Put, createIDUserIDRoomObject(idUser, idRoom));
    }

    public bool removeUserFromRoom(int idUser, int idRoom)
    {
        return messenger.Obtain<bool>(getController(), "RemoveUserFromRoom", HttpMethod.Delete, createIDUserIDRoomObject(idUser, idRoom));
    }

    private object createRoomObject(Room room)
    {
        return new RoomObject(room,loginHeader.Login,loginHeader.Password);
    }

    private object createIDUserIDRoomObject(int idUser, int idRoom)
    {
        return new IDUserIDRoomObject(idUser, idRoom,loginHeader.Login,loginHeader.Password);
    }

    public class IDUserIDRoomObject
    {

        public string Login { get; set; }
        public string Password { get; set; }
        public int IDRoom { get; set; }
        public int IDUser { get; set; }

        public IDUserIDRoomObject(int idUser, int idRoom, string Login, string Password)
        {
            this.IDRoom = idRoom;
            this.IDUser = idUser;
            this.Login = Login;
            this.Password = Password;
        }
    }

    public class RoomObject
    {
        public string Login { get; set; }
        public string Password { get; set; }
        public Room Room { get; set; }

        public RoomObject(Room room, string Login, string Password)
        {
            this.Room = room;
            this.Login = Login;
            this.Password = Password;
        }
    }



}
