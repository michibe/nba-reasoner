package org.hhz.nba_tic_tac_toe;

/**
 * Created by mbehr on 14.10.2015.
 */
public class ActivityLog {


    private final String gameId;
    private final String timestamp;
    private final Originator originator;
    private final String activity;
    private final String result;

    public ActivityLog(String gameId, String timestamp, Originator originator, String activity, String result) {
        this.gameId = gameId;
        this.timestamp = timestamp;
        this.originator = originator;
        this.activity = activity;
        this.result = result;
    }

    public ActivityLog() {
        this.gameId = null;
        this.timestamp = null;
        this.originator = null;
        this.activity = null;
        this.result = null;
    }


    public String getGameId() {
        return gameId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Originator getOriginator() {
        return originator;
    }

    public String getActivity() {
        return activity;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ActivityLog{" +
                "gameId='" + gameId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", originator=" + originator +
                ", activity='" + activity + '\'' +
                '}';
    }
}
