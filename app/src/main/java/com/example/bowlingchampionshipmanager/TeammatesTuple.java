package com.example.bowlingchampionshipmanager;

import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import java.util.ArrayList;

public class TeammatesTuple {

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "teamsid")
    private ArrayList<Integer> listids;

    public ArrayList<Integer> getListids() {
        return listids;
    }

    public void setListids(ArrayList<Integer> listids) {
        this.listids = listids;
    }


}
