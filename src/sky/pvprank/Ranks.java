package sky.pvprank;

import java.util.Map;

public class Ranks {
    private int nextRank;
    private DatabaseHandler databaseHandler;
    private Map<Integer, String> rankList;
    private Map<Integer, Integer> reqFame;
    @SuppressWarnings("unused")
    private PvPTitles pvpTitles;

    public Ranks(DatabaseHandler databaseHandler, PvPTitles pvpTitles) {
        this.databaseHandler = databaseHandler;
        this.databaseHandler.LoadConfig();
        this.rankList = this.databaseHandler.RankList();
        this.reqFame = this.databaseHandler.reqFame();
        this.pvpTitles = pvpTitles;
    }

    public String GetRank(int fame) {
        String rank = "";

        this.reqFame = this.databaseHandler.reqFame();

        for (int i = 0; i < this.reqFame.size(); i++) {
            if (fame >= this.reqFame.get(0) && fame < this.reqFame.get(1)) {
                if (!this.rankList.get(0).equalsIgnoreCase("士兵")) {
                    rank = this.rankList.get(0);
                }
                this.nextRank = this.reqFame.get(1) - fame;
                break;
            }
            if (fame >= this.reqFame.get(i) && fame < this.reqFame.get(i + 1)) {
                rank = this.rankList.get(i);
                this.nextRank = this.reqFame.get(i + 1) - fame;
                break;
            }
            if (fame >= this.reqFame.get(this.reqFame.values().size() - 1)) {
                rank = this.rankList.get(this.reqFame.values().size() - 1);
                this.nextRank = 999999;
                break;
            }
        }
        return rank;
    }

    public int FameToRankUp() {
        return this.nextRank;
    }
}
