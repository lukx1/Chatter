package net.lukx.jchatter.lib.models;

import net.lukx.jchatter.lib.PublicApi;

@PublicApi
public enum UserStatus {
    NONE(0),
    ONLINE(1),
    AWAY(2),
    OFFLINE(3),
    BUSY(4),
    ;

    private final int key;

    UserStatus(int statusFlagValue){
        this.key = statusFlagValue;
    }

    public int getKey() {
        return key;
    }

    @PublicApi
    public static UserStatus fromKey(int key){
        for(UserStatus type : UserStatus.values()) {
            if(type.getKey() == key) {
                return type;
            }
        }
        return NONE;
    }

}
