package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "pins_points", foreignKeys = {
                @ForeignKey(entity = Championship.class,
                        parentColumns = "champ_uuid",
                        childColumns = "champ_uuid")
        }, indices= {
                @Index(name="index_champID", value="champ_uuid")
        }  )
public class Pins_points {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private String pins_uuid;

    @ColumnInfo(name="champ_uuid")
    @NonNull
    private String champ_uuid;

    @ColumnInfo(name="pins")
    private int pins; //mexri poses korines

    @ColumnInfo(name="points")
    private int points; //posoi va8moi

    @NonNull
    public String getPins_uuid() {
        return pins_uuid;
    }

    @NonNull
    public String getChamp_uuid() {
        return champ_uuid;
    }

    public int getPins() {
        return pins;
    }

    public int getPoints() {
        return points;
    }

    public void setPins_uuid(@NonNull String pins_uuid) {
        this.pins_uuid = pins_uuid;
    }

    public void setChamp_uuid(@NonNull String champ_uuid) {
        this.champ_uuid = champ_uuid;
    }

    public void setPins(int pins) {
        this.pins = pins;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Pins_points(@NonNull String pins_uuid, @NonNull String champ_uuid, int pins, int points) {
        this.pins_uuid = pins_uuid;
        this.champ_uuid = champ_uuid;
        this.pins = pins;
        this.points = points;
    }
}
