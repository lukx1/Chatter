package net.lukx.jchatter.lib.models;

import net.lukx.jchatter.lib.PublicApi;

import java.util.EnumSet;

/***
 * Types of relationships between users. Flags
 */
@SuppressWarnings({"PointlessBitwiseExpression", "unused"})
@PublicApi
public enum RelationshipStatus {
    NONE(0),
    FRIENDSHIP_PENDING(1<<0),
    FRIEND(1 << 1),
    BLOCKED(1<<2),
    ;

    private final int key;

    /***
     * Creates relationship status from flags in int
     * @param statusFlagValue int with flags
     */
    RelationshipStatus(int statusFlagValue){
        this.key = statusFlagValue;
    }

    /***
     * Gets key this was set to
     * @return key
     */
    public int getKey() {
        return key;
    }

    /***
     * Creates enumset from keys
     * @param keys keys
     * @return enumset
     */
    @PublicApi
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

}
