package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface Pins_pointsDao {
    @Insert
    void insert(Pins_points t);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Pins_points t);

    @Delete
    int delete(Pins_points t);

    //step 1 ->BowlingViewModel
    @Query("SELECT * FROM pins_points")
    LiveData<List<Pins_points>> getAllPins_points();

    @Query("SELECT * FROM pins_points WHERE champ_uuid=:cid ")
    LiveData<List<Pins_points>> getPins_pointsofChamp(String cid);

}
