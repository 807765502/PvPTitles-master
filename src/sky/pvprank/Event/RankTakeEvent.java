package sky.pvprank.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by SkySslience on 2016-09-08.
 */
public class RankTakeEvent extends Event{

    public static final HandlerList handlerList = new HandlerList();
    private Player player;
    private String rank;

    public RankTakeEvent(Player player , String rank){
        this.player = player;
        this.rank = rank;
    }



    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public Player getPlayer() {
        return player;
    }

    public String getRank() {
        return rank;
    }
}
