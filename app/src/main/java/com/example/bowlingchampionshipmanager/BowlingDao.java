package com.example.bowlingchampionshipmanager;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BowlingDao {

    @Insert
    void insert(Test_table t);

    @Query("SELECT * FROM test_table")
    LiveData<List<Test_table>> getAllBowls();

    // fetch step 1 -> editViwModel
    @Query("SELECT * FROM test_table WHERE id=:bowlId")
    LiveData<Test_table> getBwol(int bowlId);

    // update step 1 -> BowlingViewModel
    @Update
    void update(Test_table test_table);

    @Delete
    int delete(Test_table test_table);

}
