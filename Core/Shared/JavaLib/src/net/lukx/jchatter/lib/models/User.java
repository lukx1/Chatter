package net.lukx.jchatter.lib.models;

import net.lukx.jchatter.lib.PublicApi;

import java.util.Date;

/***
 * Class used for deserialization of server data
 */
@SuppressWarnings("unused")
@PublicApi
public class User {

    /***
     * ID of this usre
     */
    public int id ;
    /***
     * First name
     */
    public String firstName ;
    /***
     * Second name
     */
    public String secondName ;
    /***
     * Login, unique
     */
    public String login ;
    /***
     * Password
     */
    public String password ;
    /***
     * Picture. FK to CFile UUID
     */
    public byte[] picture;
    /***
     * Email
     */
    public String email ;
    /***
     * Date registered
     */
    public Date dateRegistered ;
    /***
     * Date last login
     */
    public Date dateLastLogin ;
    /***
     * EnumSet mapping to {@link UserStatus}
     */
    public int status ;

    public User(){

    }
}
