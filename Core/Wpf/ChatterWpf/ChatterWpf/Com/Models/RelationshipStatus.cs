using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Reflection;


//HELP

[Flags]
public enum RelationshipStatus
{
    NONE=0,
    FRIENDSHIP_PENDING=1<<0,
    FRIEND=1 << 1,
    BLOCKED=1<<2,

}

/*
     private readonly int key;

    
    RelationshipStatus(int statusFlagValue)
    {
        this.key = statusFlagValue;
    }

       public int getKey() 
       {
       return key;
        }

      [PublicApi]
    public static EnumSet<RelationshipStatus> fromKey(int keys){
        EnumSet<RelationshipStatus> relationshipStatuses = EnumSet.noneOf(RelationshipStatus.class);
        for(RelationshipStatus type : RelationshipStatus.values()) {
            if((type.getKey()&keys) > 0) {
                relationshipStatuses.add(type);
            }
        }
        if(relationshipStatuses.size() == 0){
            relationshipStatuses.add(RelationshipStatus.NONE);
        }
        return relationshipStatuses;
    }
 */
