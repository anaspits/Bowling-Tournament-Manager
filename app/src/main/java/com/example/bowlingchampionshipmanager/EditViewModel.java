package com.example.bowlingchampionshipmanager;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EditViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private BowlingDao bowlingDao;
    private TeamDao teamDao;
    private BowlingRoomDatabase bDB;

    public EditViewModel(@NonNull Application application) {
        super(application);

        Log.i(TAG, "Edit ViewModel");
        bDB= BowlingRoomDatabase.getDatabase(application);
        bowlingDao = bDB.bowlingDao();
        teamDao = bDB.teamDao();
    }

    //fetch step 2 -> EditActivity
    public LiveData<Participant> getBowl(int bowlId) {
        return bowlingDao.getBwol(bowlId);
    }

    public LiveData<Team> getTeam(int teamID) {
        return teamDao.getTeam(teamID);
    }
}
