package net.lukx.jchatter.java.fetching;

import com.sun.istack.internal.NotNull;
import javafx.scene.paint.Color;
import net.lukx.jchatter.java.controls.RoomPane;
import net.lukx.jchatter.java.supporting.DefaultStatusColorPolicy;
import net.lukx.jchatter.java.supporting.StatusColorPolicy;
import net.lukx.jchatter.lib.models.*;
import net.lukx.jchatter.lib.repos.RelationshipRepo;
import net.lukx.jchatter.lib.repos.RoomRepo;

import java.io.IOException;
import java.net.URISyntaxException;

public class RoomMarshall {

    private RoomPane roomPane;
    private RoomRepo roomRepo;
    private RelationshipRepo relRepo;
    private ContentRepository contentRepository;
    private User currentUser;
    private StatusColorPolicy statusColorPolicy = new DefaultStatusColorPolicy();

    public RoomMarshall(RoomPane roomPane, RoomRepo roomRepo, RelationshipRepo relRepo, ContentRepository contentRepository, User currentUser){
        this.roomPane = roomPane;
        this.roomRepo = roomRepo;
        this.relRepo = relRepo;
        this.contentRepository = contentRepository;
        this.currentUser = currentUser;
    }

    private User getOtherUserInRoom(Room room) throws IOException, URISyntaxException {
        User[] users = roomRepo.getUsersInRoom(room.id);
        User other;
        if(users[0] == currentUser){
            other = users[1];
        }
        else {
            other = users[0];
        }
        return other;
    }

    private Relationship getRelationshipWithOther(User other) throws IOException, URISyntaxException {
        Relationship[] rels = relRepo.getRelForUser(other.id);
        Relationship withOter = null;
        for (Relationship rel : rels) {
            if(rel.idtargetUser == other.id){
                withOter = rel;
            }
        }
        return withOter;
    }

    private void initOneOnOneRoom(@NotNull RoomPane.InnerRoomPane inner, Room room) throws IOException, URISyntaxException {
        User other = getOtherUserInRoom(room);
        Relationship withOter = getRelationshipWithOther(other);

        if(withOter != null && RelationshipStatus.fromKey(withOter.relationType).contains(RelationshipStatus.BLOCKED)){
            inner.setStatusColor(Color.RED);
        }
        else {
            inner.setStatusColor(statusColorPolicy.fromStatus(other.status));
        }
        inner.setImage(contentRepository.fetchImageWithFallback(other.picture));
        inner.setName(other.firstName);
    }

    private void initRoom(@NotNull RoomPane.InnerRoomPane inner, Room room){
        inner.setName(room.name);
        inner.hideStatusCircle();
        inner.setImage(contentRepository.fetchImageWithFallback(room.picture));
    }

    private void initInnerRoomPane(@NotNull RoomPane.InnerRoomPane inner, Room room) throws IOException, URISyntaxException {
        if(room.oneOnOne){
            initOneOnOneRoom(inner,room);
        }
        else {
            initRoom(inner,room);
        }
    }

    public void loadForUser() throws IOException, URISyntaxException {
        roomPane.getRoomPanes().clear();
        Room[] rooms = roomRepo.getRoomsWithUser(currentUser.id);
        for (int heightIndex = 0; heightIndex < rooms.length; heightIndex++) {
            Room room = rooms[heightIndex];
            RoomPane.InnerRoomPane inner = roomPane.new InnerRoomPane(room);
            initInnerRoomPane(inner, room);
            roomPane.getRoomPanes().add(inner);
            inner.initializeInside(heightIndex);
        }
    }

}

