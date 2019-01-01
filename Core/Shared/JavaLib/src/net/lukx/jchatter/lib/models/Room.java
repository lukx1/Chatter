package net.lukx.jchatter.lib.models;

import net.lukx.jchatter.lib.PublicApi;

/***
 * Class used for deserialization of server data
 */
@SuppressWarnings("unused")
@PublicApi
public class Room {

    /***
     * ID of this room
     */
    public int id;
    /***
     * ID of user who created this room.
     * FK to user's ID
     */
    public int idcreator;
    /***
     * Name of this room
     */
    public String name;
    /***
     * If this is only one on one
     * or for more people
     */
    public boolean oneOnOne;
    /***
     * Picture.
     * FK to CFile UUID
     */
    public byte[] picture;

    public Room(){

    }

}
