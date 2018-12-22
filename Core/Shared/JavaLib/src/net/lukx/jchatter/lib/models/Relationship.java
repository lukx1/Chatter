package net.lukx.jchatter.lib.models;

import java.util.Date;

public class Relationship {

    public int id;
    public int idsourceUser;
    public int idtargetUser;
    public int relationType;
    public Date dateCreated;

    public Relationship(){

    }
}
