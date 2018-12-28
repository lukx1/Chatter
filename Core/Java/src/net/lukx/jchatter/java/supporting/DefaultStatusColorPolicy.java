package net.lukx.jchatter.java.supporting;

import javafx.scene.paint.Color;
import net.lukx.jchatter.lib.models.UserStatus;

public class DefaultStatusColorPolicy implements StatusColorPolicy {
    @Override
    public Color getOffline() {
        return Color.GRAY;
    }

    @Override
    public Color getOnline() {
        return Color.LIGHTGREEN;
    }

    @Override
    public Color getAway() {
        return Color.LIGHTBLUE;
    }

    @Override
    public Color getBusy() {
        return Color.LIGHTYELLOW;
    }

    @Override
    public Color fromStatus(int status) {
        UserStatus realStatus = UserStatus.fromKey(status);
        switch (realStatus) {
            case ONLINE:
                return getOnline();
            case AWAY:
                return getAway();
                default:
            case NONE:
            case OFFLINE:
                return getOffline();
            case BUSY:
                return getBusy();
        }
    }
}
