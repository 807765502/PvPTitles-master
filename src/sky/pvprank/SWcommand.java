package sky.pvprank;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SWcommand implements CommandExecutor {
    private DatabaseHandler databaseHandler;

    public SWcommand(DatabaseHandler databaseHandler, Ranks ranks) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        int fame = this.databaseHandler.PlayerFame();
        Player player = (Player) sender;
        if (player.hasPermission("pvprank.sw")) {
            if (cmd.getName().equalsIgnoreCase("sw")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.GREEN + "/sw add 玩家ID 声望点数             给玩家增加声望点数");
                    player.sendMessage(ChatColor.GREEN + "/sw take 玩家ID 声望点数             扣除玩家增加声望点数");
                    player.sendMessage(ChatColor.GREEN + "/sw set 玩家ID 声望点数             设置玩家声望点数为");
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    if (args.length > 2) {
                        String targetPlayer = args[1];
                        String a = args[2];
                        if (targetPlayer != null && a != null) {
                            int i = Integer.parseInt(a);
                            this.databaseHandler.LoadPlayerFame(targetPlayer);
                            this.databaseHandler.LoadConfig();
                            fame = fame + i;
                            player.sendMessage(ChatColor.GREEN + "玩家声望点数增加" + i + "点");
                            this.databaseHandler.SavePlayerFame(targetPlayer, fame);
                            this.databaseHandler.LoadPlayerFame(targetPlayer);
                            return true;
                        }

                    }
                }
                if (args[0].equalsIgnoreCase("take")) {
                    if (args.length > 2) {
                        String targetPlayer = args[1];
                        String a = args[2];
                        if (targetPlayer != null && a != null) {
                            int i = Integer.parseInt(a);
                            this.databaseHandler.LoadPlayerFame(targetPlayer);
                            this.databaseHandler.LoadConfig();
                            fame = fame - i;
                            player.sendMessage(ChatColor.GREEN + "玩家声望点数减少" + i + "点");
                            this.databaseHandler.SavePlayerFame(targetPlayer, fame);
                            this.databaseHandler.LoadPlayerFame(targetPlayer);
                            return true;
                        }

                    }
                }
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length > 2) {
                        String targetPlayer = args[1];
                        String a = args[2];
                        if (targetPlayer != null && a != null) {
                            int i = Integer.parseInt(a);
                            this.databaseHandler.LoadPlayerFame(targetPlayer);
                            this.databaseHandler.LoadConfig();
                            fame = i;
                            player.sendMessage(ChatColor.GREEN + "玩家声望被设置为" + i + "点");
                            this.databaseHandler.SavePlayerFame(targetPlayer, fame);
                            this.databaseHandler.LoadPlayerFame(targetPlayer);
                            return true;
                        }

                    }
                }
            }
        } else {
            player.sendMessage("§4[PVPrank]你没有权限这么做");
        }
        return true;
    }

}