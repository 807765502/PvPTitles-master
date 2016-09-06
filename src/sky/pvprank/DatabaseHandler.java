package sky.pvprank;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHandler {
    private PvPTitles pvpTitles;
    private int Fame;
    private Map<Integer, String> rankList;
    private Map<Integer, Integer> reqFame;
    private Map<String , Integer> addHealth;
    public Map<String , String> chatPrefix;
    private String tag;
    private Boolean usePapi;

    public DatabaseHandler(PvPTitles pvpTitles) {
        this.rankList = new HashMap<Integer, String>();
        this.reqFame = new HashMap<Integer, Integer>();
        this.addHealth = new HashMap<String, Integer>();
        this.chatPrefix = new HashMap<String, String>();
        this.pvpTitles = pvpTitles;
        this.SaveConfig();
    }

    public int PlayerFame() {
        return this.Fame;
    }

    public Map<Integer, String> RankList() {
        return this.rankList;
    }

    public Map<String, Integer> HealthList() {
        return this.addHealth;
    }

    public Map<String, String> ChatPrefix() {
        return this.chatPrefix;
    }

    public Map<Integer, Integer> reqFame() {
        return this.reqFame;
    }

    public String getTag() {
        return this.tag;
    }


    public void SavePlayerFame(String playername, int fame) {
        File file = new File((new StringBuilder()).append(this.pvpTitles.getDataFolder()).append(File.separator).append("players").append(File.separator).append(playername).append(".yml").toString());

        if (!file.exists()) {
            this.pvpTitles.getDataFolder().mkdir();

            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("Fame", fame);

        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LoadPlayerFame(String playername) {
        File file = new File((new StringBuilder()).append(this.pvpTitles.getDataFolder()).append(File.separator).append("players").append(File.separator).append(playername).append(".yml").toString());

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        this.Fame = config.getInt("Fame");
    }

    public void FirstRun(String playername) {
        File file = new File((new StringBuilder()).append(this.pvpTitles.getDataFolder()).append(File.separator).append("players").append(File.separator).append(playername).append(".yml").toString());

        if (!file.exists()) {
            this.pvpTitles.getDataFolder().mkdirs();

            FileConfiguration config = new YamlConfiguration();
            config.set("Fame", 0);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void SaveConfig() {
        File file = new File(this.pvpTitles.getDataFolder(), "config.yml");

        if (!file.exists()) {
            this.pvpTitles.getDataFolder().mkdirs();

            FileConfiguration config = new YamlConfiguration();

            String[] ranks =
                    {
                            "英勇黄铜",
                            "不屈白银",
                            "荣耀黄金",
                            "华贵白金",
                            "璀璨钻石",
                            "超凡大师",
                            "最强王者",
                    };

            Integer[] reqfame =
                    {
                            0,
                            1000,
                            3000,
                            5000,
                            7000,
                            9000,
                            12000,
                    };

            Integer[] health =
                    {
                            1,
                            2,
                            3,
                            4,
                            5,
                            5,
                            6,
                    };

            String[] prefix =
                    {
                            "&1英勇黄铜",
                            "&2不屈白银",
                            "&3荣耀黄金",
                            "&4华贵白金",
                            "&5璀璨钻石",
                            "&6超凡大师",
                            "&7最强王者",
                    };

            config.set("Tag", "声望");
            config.set("RankNames", Arrays.asList(ranks));
            config.set("ReqFame", Arrays.asList(reqfame));
            config.set("AddHealth" , Arrays.asList(health));
            config.set("Prefix" , Arrays.asList(prefix));
            config.set("UsePaPi" , false);
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void LoadConfig() {
        File file = new File(this.pvpTitles.getDataFolder(), "config.yml");

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<Integer> addhealth = config.getIntegerList("AddHealth");

        List<String> chapreix = config.getStringList("Prefix");

        @SuppressWarnings("unchecked")
        List<String> configList = (List<String>) config.getList("RankNames");

        for (int i = 0; i < configList.size(); i++) {
            this.rankList.put(i, configList.get(i));
            this.addHealth.put(configList.get(i) , addhealth.get(i));
            this.chatPrefix.put(configList.get(i) , chapreix.get(i));
        }

        @SuppressWarnings("unchecked")
        List<Integer> derp = (List<Integer>) config.getList("ReqFame");

        for (int i = 0; i < derp.size(); i++) {
            this.reqFame.put(i, derp.get(i));
        }

        tag = config.getString("Tag");
        usePapi = config.getBoolean("UsePaPi");

        if (configList.size() != derp.size()) {
            this.pvpTitles.log.info("警告 -配置文件出错请检查 .");

        }
    }
}
