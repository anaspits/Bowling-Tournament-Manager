package com.example.bowlingchampionshipmanager;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class ActiveChampsTuple { //relationship many to many
   /* @ColumnInfo(name = "activeChampsid")
    private ArrayList<Integer> listids;

    public ArrayList<Integer> getListids() {
        return listids;
    }

    public void setListids(ArrayList<Integer> listids) {
        this.listids = listids;
    } */


    @Embedded
    public Championship c;

    @Relation(parentColumn = "sys_champID", entityColumn = "sys_teamID", associateBy = @Junction(Championship_detail.class))
    List<Team> t;

    public void setC(Championship c) {
        this.c = c;
    }

    public void setT(List<Team> t) {
        this.t = t;
    }

    public Championship getC() {
        return c;
    }

    public List<Team> getT() {
        return t;
    }
}
