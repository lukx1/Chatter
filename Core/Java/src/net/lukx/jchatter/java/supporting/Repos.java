package net.lukx.jchatter.java.supporting;

import net.lukx.jchatter.lib.comms.Communicator;
import net.lukx.jchatter.lib.repos.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;

public class Repos {

    private CFileRepo cFileRepo;
    private MessageRepo messageRepo;
    private RelationshipRepo relationshipRepo;
    private RoomRepo roomRepo;
    private UserRepo userRepo;

    public CFileRepo getcFileRepo() {
        return cFileRepo;
    }

    public void setcFileRepo(CFileRepo cFileRepo) {
        this.cFileRepo = cFileRepo;
    }

    public MessageRepo getMessageRepo() {
        return messageRepo;
    }

    public void setMessageRepo(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    public RelationshipRepo getRelationshipRepo() {
        return relationshipRepo;
    }

    public void setRelationshipRepo(RelationshipRepo relationshipRepo) {
        this.relationshipRepo = relationshipRepo;
    }

    public RoomRepo getRoomRepo() {
        return roomRepo;
    }

    public void setRoomRepo(RoomRepo roomRepo) {
        this.roomRepo = roomRepo;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    private Communicator communicator = new Communicator();

    private HashSet<AbstractRepo> repos = new HashSet<>(5);

    public Repos(URI serverUri){
        communicator.setServerURI(serverUri);

        repos.add(cFileRepo = new CFileRepo(communicator));
        repos.add(messageRepo = new MessageRepo(communicator));
        repos.add(relationshipRepo = new RelationshipRepo(communicator));
        repos.add(roomRepo = new RoomRepo(communicator));
        repos.add(userRepo = new UserRepo(communicator));
    }

    public boolean tryChangeLogin(String username,String password) throws IOException, URISyntaxException {
        String oldLogin = userRepo.getLoginHeader().getLogin();
        String oldPassword = userRepo.getLoginHeader().getPassword();

        changeAllLogins(username,password);

        if(!userRepo.validateLogin()){
            changeAllLogins(oldLogin,oldPassword);
            return false;
        }
        else{
            return true;
        }
    }

    private void changeAllLogins(String login,String password){
        for (AbstractRepo repo : repos) {
            repo.getLoginHeader().setLogin(login);
            repo.getLoginHeader().setPassword(password);
        }
    }

    public void init(String username, String password){
        changeAllLogins(username,password);
    }

}
