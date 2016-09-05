package sky.pvprank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sky.pvprank.api.PvpTitleApi;

import java.util.HashMap;
import java.util.Map;

public class HandlePlayerPrefix implements Listener {
    private DatabaseHandler databaseHandler;
    private Ranks ranks;
    @SuppressWarnings("unused")
    private PvPTitles pvpTitles;

    public HandlePlayerPrefix(DatabaseHandler databaseHandler, Ranks ranks, PvPTitles pvpTitles) {
        this.databaseHandler = databaseHandler;
        this.ranks = ranks;
        this.pvpTitles = pvpTitles;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();

        try {
            this.databaseHandler.FirstRun(player.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        this.map.put(player.getName(), 0);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String rank = null;
        int heal = 20;
        Player player = event.getPlayer();

        this.databaseHandler.LoadPlayerFame(event.getPlayer().getName());
        this.databaseHandler.LoadConfig();

        rank = this.ranks.GetRank(this.databaseHandler.PlayerFame());

        for(String ranks : this.databaseHandler.HealthList().keySet()){
            if(rank.equalsIgnoreCase(ranks)){
                String a = PvpTitleApi.getPlayerPrefix(player);
                String format = event.getFormat();
                event.setFormat(a + format);
            }
        }
//        if (rank.equals("英勇黄铜")) {
//            String a = String.format(ChatColor.BLACK + "[" + ChatColor.GOLD + rank + ChatColor.BLACK + "] ");
//            String format = event.getFormat();
//            event.setFormat(a + format);
//            player.setMaxHealth(heal);
//        }
//        if (rank.equals("不屈白银")) {
//            String a = String.format(ChatColor.BLACK + "[" + ChatColor.GRAY + rank + ChatColor.BLACK + "] ");
//            String format = event.getFormat();
//            event.setFormat(a + format);
//            player.setMaxHealth(heal);
//        }
//        if (rank.equals("荣耀黄金")) {
//            String a = String.format(ChatColor.BLACK + "[" + ChatColor.YELLOW + rank + ChatColor.BLACK + "] ");
//            String format = event.getFormat();
//            event.setFormat(a + format);
//            player.setMaxHealth(heal + 2);
//        }
//        if (rank.equals("华贵白金")) {
//            String a = String.format(ChatColor.BLACK + "[" + ChatColor.AQUA + rank + ChatColor.BLACK + "] ");
//            String format = event.getFormat();
//            event.setFormat(a + format);
//            player.setMaxHealth(heal + 4);
//        }
//        if (rank.equals("璀璨钻石")) {
//            String a = String.format(ChatColor.BLACK + "[" + ChatColor.DARK_BLUE + rank + ChatColor.BLACK + "] ");
//            String format = event.getFormat();
//            event.setFormat(a + format);
//            player.setMaxHealth(heal + 6);
//        }
//        if (rank.equals("超凡大师")) {
//            String a = String.format(ChatColor.BLACK + "[" + "§b超§9凡§3大§e师" + ChatColor.BLACK + "] ");
//            String format = event.getFormat();
//            event.setFormat(a + format);
//            player.setMaxHealth(heal + 8);
//        }
//        if (rank.equals("最强王者")) {
//            String a = String.format(ChatColor.BLACK + "[" + "§4最§e强§d王§5者" + ChatColor.BLACK + "] ");
//            String format = event.getFormat();
//            event.setFormat(a + format);
//            player.setMaxHealth(heal + 10);
//        }
    }

    Map<String, Integer> map = new HashMap<String, Integer>();

    @EventHandler
    public void onKill(PlayerDeathEvent death) {
        int kills = 0;

        if (death.getEntity().getKiller() instanceof Player) {
            String killed = death.getEntity().getName();
            Player player = death.getEntity().getKiller();

            if (this.map.containsKey(player.getName())) {
                kills = this.map.get(player.getName());
            }
            if (this.map.containsKey(killed)) {
                this.map.put(killed, 0);
            }

            this.databaseHandler.LoadPlayerFame(player.getName());
            int fame = this.databaseHandler.PlayerFame();

            if (!player.getName().equalsIgnoreCase(killed)) {
                this.calculateFame(killed, player, fame, kills);
            }

            kills++;
            this.map.put(player.getName(), kills);
        }
    }

    @EventHandler
    public void ondeath(PlayerDeathEvent death) {
        if (death.getEntity().getKiller() != null) {
            Player deathplayer = death.getEntity();
            this.databaseHandler.LoadPlayerFame(deathplayer.getName());
            int fame = this.databaseHandler.PlayerFame();
            if (fame > 3000) {
                fame = fame - 20;
                deathplayer.sendMessage("§4[PVPrank]由于你死亡你的点数扣除20点.");
            }
            if (fame <= 500) {
                deathplayer.sendMessage("§4[PVPrank]由于你死亡你的点数扣除10点！.");
            }
            if (fame <= 0) {
                deathplayer.sendMessage("§4[PVPrank]你的荣誉点已经不够了哦！.");
            }
            this.databaseHandler.SavePlayerFame(deathplayer.getName(), fame);
            this.databaseHandler.LoadPlayerFame(deathplayer.getName());
        }
    }

    private void calculateFame(String killed, Player player, int fame, int kills) {
        if (player.hasPermission("pvprank.fame")) {
            int a = this.databaseHandler.PlayerFame();
            String tag = this.databaseHandler.getTag();
            if (kills == 0) {
                fame++;
                player.sendMessage(ChatColor.GREEN + "你击杀了 " + killed + " 并获得 1点 " + tag + ".");
            }
            if (kills == 1) {
                fame = fame + 1;
                player.sendMessage(ChatColor.GREEN + "你击杀了 " + killed + " 并获得 1点 " + tag + ".");
            }
            if (kills == 2) {
                fame = fame + 1;
                player.sendMessage(ChatColor.GREEN + "你击杀了 " + killed + " 并获得 1点" + tag + ".");
            }
            if (kills == 3) {
                fame = fame + 2;
                player.sendMessage(ChatColor.GREEN + "你击杀了 " + killed + " 并获得 2点" + tag + ".");
            }
            if (kills == 4) {
                fame = fame + 3;
                player.sendMessage(ChatColor.GREEN + "你击杀了 " + killed + " 并获得 3点" + tag + ".");
            }
            if (kills == 5) {
                fame = fame + 4;
                player.sendMessage(ChatColor.GREEN + "你击杀了 " + killed + " 并获得 4点" + tag + ".");
            }
            if (kills > 5) {
                fame = fame + 4;
                player.sendMessage(ChatColor.GREEN + "你击杀了 " + killed + " 并获得 4点" + tag + ".");
            }


            this.databaseHandler.SavePlayerFame(player.getName(), fame);
            this.databaseHandler.LoadPlayerFame(player.getName());

            String currentRank = this.ranks.GetRank(a);
            String newRank = this.ranks.GetRank(fame);

            if (!currentRank.equalsIgnoreCase(newRank)) {
                player.sendMessage(ChatColor.GREEN + "恭喜你成功晋级为 " + newRank);
                Bukkit.broadcastMessage(ChatColor.BLUE + "[军衔系统]" + ChatColor.AQUA + "恭喜" + player.getName() + "成功晋级为" + newRank + "," + ChatColor.DARK_BLUE + "他的血量也变得更多了");
                int addHealth = this.databaseHandler.HealthList().get(newRank);
                player.setMaxHealth(20 + addHealth);
            }
        }
    }
}