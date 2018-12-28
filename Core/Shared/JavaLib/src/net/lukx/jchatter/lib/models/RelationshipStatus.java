package net.lukx.jchatter.lib.models;

import java.util.EnumSet;

public enum RelationshipStatus {
    NONE(0),
    FRIENDSHIP_PENDING(1<<0),
    FRIEND(1 << 1),
    BLOCKED(1<<2),
    ;

    private final int key;

    RelationshipStatus(int statusFlagValue){
        this.key = statusFlagValue;
    }

    public int getKey() {
        return key;
    }

    public static EnumSet<RelationshipStatus> fromKey(int key){
        EnumSet<RelationshipStatus> relationshipStatuses = EnumSet.noneOf(RelationshipStatus.class);
        for(RelationshipStatus type : RelationshipStatus.values()) {
            if((type.getKey()&key) > 0) {
                relationshipStatuses.add(type);
            }
        }
        if(relationshipStatuses.size() == 0){
            relationshipStatuses.add(RelationshipStatus.NONE);
        }
        return relationshipStatuses;
    }
}
