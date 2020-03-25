package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "hdcpparameters")
public class HDCPparameters implements Serializable { //axristo?
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int hdcpparid;

    @ColumnInfo(name="champID")
    @NonNull
    private int champID;

    @ColumnInfo(name="begBS")
    private int begBS; //begginers' basis score

    @ColumnInfo(name="advBS")
    private int advBS; //advanced's basis score

    @ColumnInfo(name="lessBS")
    private int lessBS; //for_teams_with_less_players

    @ColumnInfo(name="factor")
    private int factor; //percentage factor in %

    @ColumnInfo(name="tavani")
    private int tavani;

    public int getHdcpparid() {
        return hdcpparid;
    }

    public int getChampID() {
        return champID;
    }

    public int getBegBS() {
        return begBS;
    }

    public int getAdvBS() {
        return advBS;
    }

    public int getLessBS() {
        return lessBS;
    }

    public int getFactor() {
        return factor;
    }

    public int getTavani() {
        return tavani;
    }

    public void setHdcpparid(int hdcpparid) {
        this.hdcpparid = hdcpparid;
    }

    public void setChampID(int champID) {
        this.champID = champID;
    }

    public void setBegBS(int begBS) {
        this.begBS = begBS;
    }

    public void setAdvBS(int advBS) {
        this.advBS = advBS;
    }

    public void setLessBS(int lessBS) {
        this.lessBS = lessBS;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public void setTavani(int tavani) {
        this.tavani = tavani;
    }

    public HDCPparameters( int champID, int begBS, int advBS, int lessBS, int factor, int tavani) {
        this.champID = champID;
        this.begBS = begBS;
        this.advBS = advBS;
        this.lessBS = lessBS;
        this.factor = factor;
        this.tavani = tavani;
    }

}
