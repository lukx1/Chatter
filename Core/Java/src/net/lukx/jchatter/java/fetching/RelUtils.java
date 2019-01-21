package net.lukx.jchatter.java.fetching;

import net.lukx.jchatter.java.supporting.Repos;
import net.lukx.jchatter.lib.models.Relationship;
import net.lukx.jchatter.lib.models.RelationshipStatus;
import net.lukx.jchatter.lib.models.User;

import java.io.IOException;
import java.net.URISyntaxException;

public class RelUtils {

    public static boolean isUser(RelationshipStatus status, Repos repos, User targetUser, User currentUser) throws IOException, URISyntaxException {
        Relationship[] rels = repos.getRelationshipRepo().getRelForUser(currentUser.id);
        for (Relationship rel : rels) {
            if(rel.idtargetUser == targetUser.id){
                return(RelationshipStatus.fromKey(rel.relationType).contains(status));
            }
        }
        return false;
    }
    public static void changeRelWithUser(RelationshipStatus status, Repos repos, User targetUser, User currentUser) throws IOException, URISyntaxException {
        changeRel(status,repos, targetUser, currentUser);
        changeRel(status,repos, currentUser, targetUser);
    }

    private static void changeRel(RelationshipStatus status,Repos repos, User targetUser, User currentUser) throws IOException, URISyntaxException {
        Relationship[] myRels = repos.getRelationshipRepo().getRelForUser(currentUser.id);
        for (Relationship myRel : myRels) {
            if (myRel.idtargetUser == targetUser.id) {
                myRel.relationType = status.getKey();
                repos.getRelationshipRepo().setRel(myRel);
                break;
            }
        }
    }
}
