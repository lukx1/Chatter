package net.lukx.jchatter.lib.comms;

import com.sun.istack.internal.NotNull;
import net.lukx.jchatter.lib.PublicApi;

/***
 * Header needed to use some of Chatter's
 * endpoints
 */
@PublicApi
public class LoginHeader {

    private String Login;
    private String Password;

    /**
     * @return login
     */
    public String getLogin() {
        return Login;
    }

    /**
     * @param login sets
     */
    @PublicApi
    public void setLogin(@NotNull String login) {
        Login = login;
    }

    /**
     * @return password
     */
    @PublicApi
    public String getPassword() {
        return Password;
    }

    /**
     * @param s password
     */
    @PublicApi
    public void setPassword(@NotNull String s) {
        Password = s;
    }
}
