using System;
using System.Linq;
using System.Net.Http;
using System.Web;
using ChatterWpf.Comms;
using ChatterWpf.Models;

public class RelationshipRepo : AbstractRepo
{
    public RelationshipRepo(Messenger messenger) : base(messenger)
    {

    }

    protected override string getController()
    {
        return "Relationship";
    }

    public Relationship[] getRelForUser(int id)
    {
        return messenger.Obtain<Relationship[]>(
                getController(),
                "GetRelForUser",
                HttpMethod.Post,
                createIdObject(id)
                );
    }

    public void setRel(Relationship relationship)
    {
        messenger.Obtain(getController(), "Rel", HttpMethod.Post, createRelObject(relationship));
    }

    public bool deleteRel(int id)
    {
        return messenger.Obtain<bool>(getController(), "Rel", HttpMethod.Delete, createIdObject(id));
    }

    public void addRel(Relationship relationship)
    {
        messenger.Obtain(getController(), "Rel", HttpMethod.Put, createRelObject(relationship));
    }

    private object createRelObject(Relationship relationship)
    {
        return new RelObject(relationship,loginHeader.Login,loginHeader.Password);
    }

    public class RelObject
    {
        public string Login { get; set; }
        public string Password { get; set; }
        public Relationship Relationship { get; set; }

        public RelObject(Relationship relationship, string Login,string Password)
        {
            this.Relationship = relationship;
            this.Login = Login;
            this.Password = Password;
        }
    }
}
