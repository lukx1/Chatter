package net.lukx.jchatter.lib.repos;

import net.lukx.jchatter.lib.PublicApi;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.Relationship;

import java.io.IOException;
import java.net.URISyntaxException;

/***
 * Repo for obtaining relationships
 * from the server
 */
@PublicApi
public class RelationshipRepo extends AbstractRepo {

    /***
     * Creates an instance of this class
     * @param communicator to send requests with
     */
    public RelationshipRepo(Communicator communicator) {
        super(communicator);
    }

    /***
     * {@inheritDoc}
     */
    @Override
    protected String getController() {
        return "Relationship";
    }

    /***
     * Gets all relationships a user has with any other user
     * @param id of the user
     * @return relationships
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public Relationship[] getRelForUser(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(
                getController(),
                "GetRelForUser",
                Communicator.HttpMethod.POST,
                createIdObject(id),
                Relationship[].class);
    }

    /***
     * Updates information about a specified relationship
     * @param relationship to set
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public void setRel(Relationship relationship) throws IOException, URISyntaxException {
        communicator.Obtain(getController(), "Rel", Communicator.HttpMethod.POST, createRelObject(relationship), Void.class);
    }

    /***
     * Deletes a relationship
     * @param id of the relationship
     * @return true if removed, false otherwise
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public boolean deleteRel(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(), "Rel", Communicator.HttpMethod.DELETE, createIdObject(id), boolean.class);
    }

    /***
     * Creates a new relationship
     * @param relationship to add
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public void addRel(Relationship relationship) throws IOException, URISyntaxException {
        communicator.Obtain(getController(), "Rel", Communicator.HttpMethod.PUT, createRelObject(relationship), Void.class);
    }

    private Object createRelObject(Relationship relationship) {
        return new RelObject(relationship);
    }


    @SuppressWarnings("unused")
    private class RelObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private Relationship Relationship;

        RelObject(Relationship relationship) {
            this.Relationship = relationship;
        }
    }
}
