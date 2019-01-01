package net.lukx.jchatter.lib.models;

import net.lukx.jchatter.lib.PublicApi;

import java.util.Date;

/***
 * Class used for deserialization of server data
 */
@SuppressWarnings("unused")
@PublicApi
public class Relationship {

    /***
     * ID of this relationship
     */
    public int id;
    /***
     * ID of user who created this relationship.
     * FK to user's ID
     */
    public int idsourceUser;
    /***
     * ID of user to whom this applies.
     * FK to user's ID
     */
    public int idtargetUser;
    /***
     * Type of relationship mapping to {@link RelationshipStatus}
     */
    public int relationType;
    /***
     * When this relationship was created
     */
    public Date dateCreated;

    public Relationship(){

    }
}
