package net.lukx.jchatter.lib.repos;

import com.sun.istack.internal.NotNull;
import net.lukx.jchatter.lib.PublicApi;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URISyntaxException;

/***
 * Repo for obtaining users
 * from the server
 */
@PublicApi
public class UserRepo extends AbstractRepo {

    /***
     * {@inheritDoc}
     */
    @Override
    protected String getController() {
        return "User";
    }

    /***
     * Creates an instance of this class
     * @param communicator to send requests with
     */
    public UserRepo(net.lukx.jchatter.lib.comms.Communicator communicator) {
        super(communicator);
    }

    /***
     * Gets all users in the whole database
     * @return users
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public User[] getUsers() throws IOException, URISyntaxException {
        return communicator.Obtain(getController(), "GetUsers", Communicator.HttpMethod.POST, getLoginHeader(), User[].class);
    }

    /***
     * Gets user with specific id
     * @param id of the user
     * @return user
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public User getUser(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(), "GetUser", Communicator.HttpMethod.POST, createIdObject(id), User.class);
    }

    /***
     * Gets user with specific login or null
     * @param login of the user
     * @return user
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public User getUserWithLogin(@NotNull String login) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(), "GetUserWithLogin", Communicator.HttpMethod.POST, new LoginObject(login), User.class);
    }

    /***
     * Registers a new user.
     * @param user to register
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public void registerUser(@NotNull User user) throws IOException, URISyntaxException {
        communicator.Obtain(getController(), "RegisterUser", Communicator.HttpMethod.PUT, user, Void.class);
    }

    /***
     * Checks against the database if login is correct
     * @return true if valid, false otherwise
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public boolean validateLogin() throws IOException, URISyntaxException {
        return communicator.Obtain(getController(), "ValidateLogin", Communicator.HttpMethod.POST, getLoginHeader(), boolean.class);
    }

    /***
     * Deletes user with specified id Only if the login info matches the user's.
     * @param id of user
     * @return true if deleted, false otherwise
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public boolean deleteUser(int id) throws IOException, URISyntaxException {
        return communicator.Obtain(getController(), "User", Communicator.HttpMethod.DELETE, createIdObject(id), boolean.class);
    }

    /***
     * Changes a specified users values. Only if login information matches users'.
     * @param user to change
     * @throws IOException if exception occurs
     * @throws URISyntaxException if uri is malformed
     */
    @PublicApi
    public void setUser(User user) throws IOException, URISyntaxException {
        communicator.Obtain(getController(), "User", Communicator.HttpMethod.POST, getLoginHeader(), Void.class);
    }

    @SuppressWarnings("unused")
    private class LoginObject {
        private String Login = getLoginHeader().getLogin();
        private String Password = getLoginHeader().getPassword();
        private String UserLogin;

        LoginObject(String userLogin) {
            this.UserLogin = userLogin;
        }
    }
}
