package net.lukx.jchatter.lib.models;

import java.util.Date;

public class User {

    public int id ;
    public String firstName ;
    public String secondName ;
    public String login ;
    public String password ;
    public byte[] picture;
    public String email ;
    public Date dateRegistered ;
    public Date dateLastLogin ;
    public int status ;

    public User(){

    }
}
