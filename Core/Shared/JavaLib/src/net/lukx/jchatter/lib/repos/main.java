/*package net.lukx.jchatter.lib.repos;

import com.google.gson.Gson;
import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class main {

    public class Test {
        public int ID = 127;
        public String Name = "asdasd";
    }

    private void run(String[] args){
        Communicator c = new Communicator();
        try {
            c.setServerURI(new URI("http://localhost:5000/api"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        UserRepo r = new UserRepo(c);
        r.getLoginHeader().setLogin("aasdsad");
        r.getLoginHeader().setPassword("ewqeq");
        try {
            User[] users = r.getUsers();
            int z = 124;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        int x = 10;
    }

    public static void main(String[] args){
        new main().run(null);
    }
}
*/