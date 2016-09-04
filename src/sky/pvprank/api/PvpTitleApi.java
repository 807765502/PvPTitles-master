package sky.pvprank.api;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import sky.pvprank.DatabaseHandler;
import sky.pvprank.Ranks;

/**
 * Created by SkySslience on 2016-09-04.
 */
public class PvpTitleApi {

    private static DatabaseHandler databaseHandler;
    private static Ranks ranks;


    public PvpTitleApi(DatabaseHandler databaseHandler, Ranks ranks) {
        this.databaseHandler = databaseHandler;
        this.ranks = ranks;
    }

    public static String getPlayerPrefix(Player player){
        String prefix = "";
        String rank = ranks.GetRank(databaseHandler.PlayerFame());
        for(String ranks : databaseHandler.chatPrefix.keySet()){
            if(rank.equalsIgnoreCase(ranks)){
                prefix = ChatColor.translateAlternateColorCodes('&' , databaseHandler.chatPrefix.get(rank));
            }
        }
        return prefix;
    }
}
