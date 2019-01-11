using System;
using System.Linq;
using System.Web;

public class RelationshipRepo : AbstractRepo
{
    public RelationshipRepo(ICommunicable communicable)
    {
        base(communicable);
    }

    protected override string getController()
    {
        return "Relationship";
    }

    public Relationship[] getRelForUser(int id)
    {
        return communicable.Obtain(
                getController(),
                "GetRelForUser",
                HttpMethod.POST,
                createIdObject(id),
                typeof(Relationship[]));
    }

    public void setRel(Relationship relationship)
    {
        communicable.Obtain(getController(), "Rel", HttpMethod.POST, createRelObject(relationship), typeof(void));
    }

    public bool deleteRel(int id)
    {
        return communicable.Obtain(getController(), "Rel", HttpMethod.DELETE, createIdObject(id), typeof(bool));
    }

    public void addRel(Relationship relationship)
    {
        communicable.Obtain(getController(), "Rel", HttpMethod.PUT, createRelObject(relationship), typeof(void));
    }

    private Object createRelObject(Relationship relationship)
    {
        return new RelObject(relationship);
    }

    private class RelObject
    {
        private string Login = getLoginHeader().getLogin();
        private string Password = getLoginHeader().getPassword();
        private Relationship Relationship;

        RelObject(Relationship relationship)
        {
            this.Relationship = relationship;
        }
    }
}
