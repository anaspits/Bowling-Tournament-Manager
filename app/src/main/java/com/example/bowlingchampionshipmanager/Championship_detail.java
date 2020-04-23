package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "championship_detail",primaryKeys = {"champ_uuid","team_uuid"}, foreignKeys = {
        @ForeignKey(entity = Team.class,
                parentColumns = "team_uuid",
                childColumns = "team_uuid"),
        @ForeignKey(entity = Championship.class,
                parentColumns = "champ_uuid",
                childColumns = "champ_uuid")
}, indices= {
        @Index(name="index_cd_teamID", value="team_uuid", unique=true),
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

    @ColumnInfo(name="active_flag") //0:energo :teleiwse
    private int active_flag;

  /*  public String getSys_champDetailID() {
        return sys_champDetailID;
    } */

    public String getSys_champID() {
        return sys_champID;
    }

    public String getSys_teamID() {
        return sys_teamID;
    }

    public int getActive_flag() {
        return active_flag;
    }
/* public void setSys_champDetailID(String sys_champDetailID) {
        this.sys_champDetailID = sys_champDetailID;
    }*/

    public void setSys_teamID(String sys_teamID) {
        this.sys_teamID = sys_teamID;
    }

    public void setSys_champID(String sys_champID) {
        this.sys_champID = sys_champID;
    }

    public void setActive_flag(int active_flag) {
        this.active_flag = active_flag;
    }

    public Championship_detail(String sys_champID, String sys_teamID) {
        this.sys_teamID = sys_teamID;
        this.sys_champID = sys_champID;
    }

}
