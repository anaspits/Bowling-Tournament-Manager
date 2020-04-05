package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "championship_detail"/*, foreignKeys = {
        @ForeignKey(entity = Team.class,
                parentColumns = "sys_teamID",
                childColumns = "sys_teamID"),
        @ForeignKey(entity = Championship.class,
                parentColumns = "sys_champID",
                childColumns = "sys_champID")
}, indices= {
        @Index(name="index_teamID", value="sys_teamID", unique=true),
        @Index(name="index_champID", value="sys_champID", unique=true)
}  primaryKeys = {"sys_champID","sys_teamID"}*/)
public class Championship_detail {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int sys_champDetailID;

    @ColumnInfo(name="sys_champID")
    @NonNull
    private int sys_champID;

    @ColumnInfo(name="sys_teamID")
    @NonNull
    private int sys_teamID;

  /*  public int getSys_champDetailID() {
        return sys_champDetailID;
    } */

    public int getSys_champID() {
        return sys_champID;
    }

    public int getSys_teamID() {
        return sys_teamID;
    }

    public void setSys_champDetailID(int sys_champDetailID) {
        this.sys_champDetailID = sys_champDetailID;
    }

    public void setSys_teamID(int sys_teamID) {
        this.sys_teamID = sys_teamID;
    }

    public void setSys_champID(int sys_champID) {
        this.sys_champID = sys_champID;
    }

    public Championship_detail(int sys_champID, int sys_teamID) {
        this.sys_teamID = sys_teamID;
        this.sys_champID = sys_champID;
    }

}
