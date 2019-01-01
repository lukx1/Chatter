package net.lukx.jchatter.lib.models;

import net.lukx.jchatter.lib.PublicApi;

import java.util.Date;

/***
 * Class used for deserialization of server data
 */
@SuppressWarnings("unused")
@PublicApi
public class CFile {
    /***
     * UUID of file
     */
    public byte[] uuid;
    /***
     * ID of the user who uploaded this file.
     * FK to User's ID
     */
    public int iduploader;
    /***
     * ID of the room this file has been uploaded to.
     * FK to Room's ID
     */
    public int idroom;
    /***
     * Name of this file
     */
    public String fileName;
    /***
     * When this file was uploaded
     */
    public Date dateUploaded;
    /***
     * If this file has expired and it's contents
     * are no longer on the server
     */
    public boolean expired;
}
