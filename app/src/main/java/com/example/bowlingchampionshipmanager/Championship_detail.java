package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "championship_detail",primaryKeys = {"champ_uuid","team_uuid"}, foreignKeys = {
        @ForeignKey(entity = Team.class,
                parentColumns = "team_uuid",
                childColumns = "team_uuid",
                onDelete = CASCADE),
        @ForeignKey(entity = Championship.class,
                parentColumns = "champ_uuid",
                childColumns = "champ_uuid",
        onDelete = CASCADE)
}, indices= {
        @Index(name="index_cd_teamID", value="team_uuid"),
        @Index(name="index_champID", value="champ_uuid")
}  )
public class Championship_detail {
   /* @PrimaryKey(autoGenerate = true)
    @NonNull
    int sys_champDetailID;*/

    @ColumnInfo(name="champ_uuid")
    @NonNull
    private String sys_champID;

    @ColumnInfo(name="team_uuid")
    @NonNull
    private String sys_teamID;

    @ColumnInfo(name="team_score")
    private int score;

    @ColumnInfo(name="active_flag") //0:energo :teleiwse
    private int active_flag;

    @ColumnInfo(name="created_at")
    private Date created_at;

  /*  public String getSys_champDetailID() {
        return sys_champDetailID;
    } */

    public String getSys_champID() {
        return sys_champID;
    }

    public String getSys_teamID() {
        return sys_teamID;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public int getActive_flag() {
        return active_flag;
    }

    public int getScore() {
        return score;
    }
/* public void setSys_champDetailID(String sys_champDetailID) {
        this.sys_champDetailID = sys_champDetailID;
    }*/

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setSys_teamID(String sys_teamID) {
        this.sys_teamID = sys_teamID;
    }

    public void setSys_champID(String sys_champID) {
        this.sys_champID = sys_champID;
    }

    public void setActive_flag(int active_flag) {
        this.active_flag = active_flag;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Championship_detail(String sys_champID, String sys_teamID, Date created_at) {
        this.sys_teamID = sys_teamID;
        this.sys_champID = sys_champID;
        this.created_at=created_at;
    }

}
