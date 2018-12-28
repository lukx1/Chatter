package net.lukx.jchatter.java.supporting;

import javafx.scene.paint.Color;
import net.lukx.jchatter.lib.models.UserStatus;

public interface StatusColorPolicy {
    Color getOffline();
    Color getOnline();
    Color getAway();
    Color getBusy();
    Color fromStatus(int status);
}
