package net.lukx.jchatter.lib.repos;

import com.google.gson.reflect.TypeToken;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.Relationship;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

public class RelationshipRepo extends AbstractRepo{

    public RelationshipRepo(Communicator communicator) {
        super(communicator);
    }

    @Override
    protected String getController() {
        return "Relationship";
    }

    public Relationship[] getRelForUser(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "GetRelForUser",
                Communicator.HttpMethod.POST,
                createIdObject(id),
               Relationship[].class);
    }

    public void setRel(Relationship relationship) throws IOException, URISyntaxException {
        communicator.Obtain(getController(),"Rel", Communicator.HttpMethod.POST,createRelObject(relationship),Void.class);
    }

    public boolean deleteRel(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(),"Rel", Communicator.HttpMethod.DELETE,createIdObject(id),boolean.class);
    }

    public void addRel(Relationship relationship) throws IOException, URISyntaxException {
        communicator.Obtain(getController(), "Rel", Communicator.HttpMethod.PUT, createRelObject(relationship), Void.class);
    }

    private Object createRelObject(Relationship relationship){
        return new RelObject(relationship);
    }

    private class RelObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private Relationship Relationship;

        RelObject(Relationship relationship){
            this.Relationship = relationship;
        }
    }
}
