package com.example.bowlingchampionshipmanager;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

public class TeammatesTuple { //relationship many to many

   /* @TypeConverters(Converters.class)
    @ColumnInfo(name = "teamsid")
    private ArrayList<Integer> listids;

    public ArrayList<Integer> getListids() {
        return listids;
    }

    public void setListids(ArrayList<Integer> listids) {
        this.listids = listids;
    }
*/

    @Embedded
    public Team c;

    @Relation(parentColumn = "team_uuid", entityColumn = "participant_uuid", associateBy = @Junction(Team_detail.class))
    List<Participant> t;

    public void setC(Team c) {
        this.c = c;
    }

    public void setT(List<Participant> t) {
        this.t = t;
    }

    public Team getC() {
        return c;
    }

    public List<Participant> getT() {
        return t;
    }

}
