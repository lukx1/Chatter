﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

[PublicApi]
public class Room
{
    public int id;

    public int idcreator;

    public string name;  
    
    public boolean oneOnOne; //If this is only one on one 

    public byte[] picture;     // FK to CFile UUID

    public Room()
    {

    }

}