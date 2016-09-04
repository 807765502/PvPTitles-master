package sky.pvprank;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RankCommand implements CommandExecutor {
    private DatabaseHandler databaseHandler;
    private Ranks ranks;

    public RankCommand(DatabaseHandler databaseHandler, Ranks ranks) {
        this.databaseHandler = databaseHandler;
        this.ranks = ranks;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (cmd.getName().equalsIgnoreCase("rank")) {
            this.HandleRankCmd(player);
        }
        if (args.length > 0) {
            player.sendMessage(ChatColor.RED + "参数错误");
        }

        return true;
    }

    private void HandleRankCmd(Player player) {
        this.databaseHandler.LoadPlayerFame(player.getName());
        this.databaseHandler.LoadConfig();
        int fame = this.databaseHandler.PlayerFame();
        String rank = this.ranks.GetRank(fame);
        int rankup = this.ranks.FameToRankUp();
        String tag = this.databaseHandler.getTag();

        if (rank == "") {
            player.sendMessage("§2====段位系统排名====");
            player.sendMessage("§2军衔等级: 无任何等级");
        } else {
            player.sendMessage("§2====段位系统排名====");
            player.sendMessage("§2军衔等级: " + rank);
        }
        player.sendMessage(ChatColor.GREEN + tag + ": " + fame);

        if (rankup == 999999) {
            player.sendMessage("§2你已经升级到最高等级.");
        } else {
            player.sendMessage("§2距下一次升级: " + rankup);
        }
    }

}
