/*package net.lukx.jchatter.lib;

import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.models.*;
import net.lukx.jchatter.lib.repos.RelationshipRepo;
import net.lukx.jchatter.lib.repos.RoomRepo;
import net.lukx.jchatter.lib.repos.UserRepo;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class main {


    public static void main(String[] args) throws URISyntaxException, UnsupportedEncodingException, InterruptedException {
        Communicator c = new Communicator(); //Creates communication client

        c.setServerURI(new URI("http://78.102.218.164:5000/"));//Sets URI

        //Prepare our repos
        RoomRepo roomRepo = new RoomRepo(c);
        roomRepo.getLoginHeader().setLogin("MyLogin");
        roomRepo.getLoginHeader().setPassword("MyPassword");

        UserRepo userRepo = new UserRepo(c);
        userRepo.getLoginHeader().setLogin("MyLogin");
        userRepo.getLoginHeader().setPassword("MyPassword");

        RelationshipRepo relRepo = new RelationshipRepo(c);
        relRepo.getLoginHeader().setLogin("MyLogin");
        relRepo.getLoginHeader().setPassword("MyPassword");
        try {

            Room myNewRoom = new Room(); // Prepare the room to upload
            myNewRoom.oneOnOne = false; // We want this to be a group chat
            myNewRoom.name = "Everyone!!!"; // We set the name
            myNewRoom.picture = null; // Picture is not mandatory

            myNewRoom = roomRepo.addRoom(myNewRoom); // Create it!

            User me = userRepo.getUserWithLogin("bobby"); // Get ourselves

            Relationship[] relationships = relRepo.getRelForUser(me.id); // Get relationships we have

            for (Relationship relationship : relationships) {
                if(relationship.relationType == RelationshipStatus.FRIEND.getKey()/*relationType is an int){
                    roomRepo.addUserToRoom(relationship.idtargetUser,myNewRoom.id); // Add our friend to the room
                    System.out.println("Added friend with id "+relationship.idtargetUser+" to the room");
                }
            }

            System.out.println("All friends have been addded");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/