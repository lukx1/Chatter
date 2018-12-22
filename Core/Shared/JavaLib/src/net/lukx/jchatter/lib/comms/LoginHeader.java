package net.lukx.jchatter.lib.comms;

import com.sun.istack.internal.NotNull;

import java.nio.charset.StandardCharsets;

public class LoginHeader {

    private String Login;
    private String Password;

    public String getLogin() {
        return Login;
    }

    public void setLogin(@NotNull String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(@NotNull String s) {
        Password = s;
    }
}
