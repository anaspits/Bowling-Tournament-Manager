package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.io.Serializable;
//axristo? //todo na svisw
@Entity(tableName = "round_detail",primaryKeys = {"round_uuid","participant_uuid"}, foreignKeys = {
        @ForeignKey(entity = Round.class,
                parentColumns = "round_uuid",
                childColumns = "round_uuid"),
        @ForeignKey(entity = Team.class,
                parentColumns = "team_uuid",
                childColumns = "team_uuid")
}, indices= {
        @Index(name="index_roundID", value="round_uuid"),
        @Index(name="index_rt_teamID", value="team_uuid")
})
public class Round_Team  implements Serializable {
   /* @PrimaryKey(autoGenerate = true)
    @NonNull
    int sys_roundDetailID; */

    @ColumnInfo(name="round_uuid")
    @NonNull
    private String round_uuid;

    @ColumnInfo(name="team_uuid")
    @NonNull
    private String team_uuid;

    @ColumnInfo(name="score") //tou participant
    private int score;

   /* public int getSys_roundDetailID() {
        return sys_roundDetailID;
    } */

    @NonNull
    public String getRound_uuid() {
        return round_uuid;
    }

    public String getTeam_uuid() {
        return team_uuid;
    }

    public int getScore() {
        return score;
    }

   /* public void setSys_roundDetailID(int sys_roundDetailID) {
        this.sys_roundDetailID = sys_roundDetailID;
    } */

    public void setRound_uuid(String round_uuid) {
        this.round_uuid = round_uuid;
    }

    public void setTeam_uuid(String team_uuid) {
        this.team_uuid = team_uuid;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Round_Team(String round_uuid, String team_uuid, int score) {
        this.round_uuid = round_uuid;
        this.team_uuid = team_uuid;
        this.score = score;
        //todo: mhpws 8elei k ton champid?
    }

}
