package sky.pvprank;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PvPTitles extends JavaPlugin {
    public Logger log;
    private RankCommand rankCommand;
    private DatabaseHandler databaseHandler;
    private Ranks ranks;
    private HandlePlayerPrefix handlePlayerPrefix;
    private LeaderBoardCommand ladder;
    private SWcommand swcommand;

    public void onEnable() {
        this.log = getLogger();
        this.databaseHandler = new DatabaseHandler(this);
        this.ranks = new Ranks(this.databaseHandler, this);
        this.rankCommand = new RankCommand(this.databaseHandler, this.ranks);
        this.handlePlayerPrefix = new HandlePlayerPrefix(this.databaseHandler, this.ranks, this);
        this.ladder = new LeaderBoardCommand(this);
        this.swcommand = new SWcommand(this.databaseHandler, this.ranks);
        getServer().getPluginManager().registerEvents(handlePlayerPrefix, this);
        getCommand("rank").setExecutor(this.rankCommand);
        getCommand("ladder").setExecutor(this.ladder);
        getCommand("sw").setExecutor(this.swcommand);
        getLogger().info("PVPrank加载成功!");
    }

    public void onDisable() {
        getLogger().info("pvprank加载失败!");
    }
}
