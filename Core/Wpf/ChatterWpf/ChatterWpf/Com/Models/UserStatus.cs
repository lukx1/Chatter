using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

//HELP
/*
 public class UserStatus : Enumeration
 {
    public static readonly UserStatus NONE
        = new UserStatus(0, "NONE");
    public static readonly UserStatus ONLINE
        = new UserStatus1, "ONLINE");
    public static readonly UserStatus AWAY
        = new UserStatus(2, "AWAY");
    public static readonly UserStatus OFFLINE
        = new UserStatus(3, "OFFLINE");
    public static readonly UserStatus BUSY
        = new UserStatus(4, "BUSY");

    private UserStatus() { }
    private UserStatus(int value, string displayValue) : base(value, displayValue) { }

    private readonly int key;

    UserStatus(int statusFlagValue){
        this.key = statusFlagValue;
    }

    public int getKey() {
        return key;
    }

     [PublicApi]
    public static UserStatus fromKey(int key)
    {
        foreach (UserStatus type in UserStatus.values())
        {
            if (type.getKey() == key)
            {
                return type;
            }
        }
        return NONE;
    }

 }
 */


[Flags]
public enum UserStatus
{
    
    NONE=0,
    ONLINE=1,
    AWAY=2,
    OFFLINE=3,
    BUSY=4
     }
static class UserStatusMethods {

    private readonly int key;

    UserStatus(int statusFlagValue)
    {
    this.key = statusFlagValue;
    }

    public int getKey()
    {
        return key;
    }

    [PublicApi]
    public static UserStatus fromKey(int key)
    {
        foreach (UserStatus type in UserStatus.values())
        {
            if (type.getKey() == key)
            {
                return type;
            }
        }
        return NONE;
    }

}
