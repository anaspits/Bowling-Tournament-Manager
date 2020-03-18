package com.example.bowlingchampionshipmanager;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BowlingViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private BowlingDao bowlingDao;
    private BowlingRoomDatabase bDB;

    private LiveData<List<Participant>> mAllNotes;
   // private LiveData<List<Participant>> testNotes;

    public BowlingViewModel(@NonNull Application application) {

        super(application);

        bDB= BowlingRoomDatabase.getDatabase(application);
        bowlingDao = bDB.bowlingDao();
        mAllNotes = bowlingDao.getAllBowls();
        //testNotes = bowlingDao.getAllPlayersofChamp(teamid);
    }


    public  void insert (Participant t){
        new InsertAsyncTask(bowlingDao).execute(t);
    }

    LiveData<List<Participant>> getAllBowls() {
        return mAllNotes;
    }

    //step 2 -> Create1Activity 157 h AddNeActivity //mhpws na to grapsw sto ediViewModel? //na to svisw
    public int getAllPlayersofChamp2(int champID) {
        return ((int) bowlingDao.getAllPlayersofChamp2(champID));
        //new getAllPlayersofChamp2AsyncTask(bowlingDao).execute(champID);
    }

    //update step 2 -> Create1Activity
    public void update(Participant test_table) {
        new UpdateAsyncTask(bowlingDao).execute(test_table); //tsekare pio katw
    }

    public void delete(Participant test_table) {
        new DeleteAsyncTask(bowlingDao).execute(test_table);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    public LiveData<List<Participant>> getAllPlayersofTeam(int teamid) {
        return bowlingDao.getAllPlayersofTeam(teamid);
    }
    public LiveData<List<Participant>> getAllPlayersofTeamOrdered() {
        return bowlingDao.getAllPlayersofTeamOrdered();
    }
    public LiveData<List<Participant>> getAllPlayersofChamp(int champid) {
        return bowlingDao.getAllPlayersofTeamOrdered(champid);
    }

    private class OperationsAsyncTask extends AsyncTask<Participant, Void, Void> {

       BowlingDao mAsyncTaskDao;

        OperationsAsyncTask(BowlingDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Participant... participants) {
            return null;
        }
    }
    private class InsertAsyncTask extends OperationsAsyncTask {

        public InsertAsyncTask(BowlingDao bDao) {
            super(bDao);
        }
        @Override
        protected Void doInBackground(Participant... participants) {
            mAsyncTaskDao.insert(participants[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(BowlingDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Participant... participants) {
            mAsyncTaskDao.update(participants[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(BowlingDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Participant... participants) {
            mAsyncTaskDao.delete(participants[0]);
            return null;
        }
    }

    private class getAllPlayersofChamp2AsyncTask extends AsyncTask<Integer, Void, Void> {

        BowlingDao mAsyncTaskDao;

        getAllPlayersofChamp2AsyncTask(BowlingDao dao) {
            this.mAsyncTaskDao = dao;
        }
        int sum;
        @Override
        protected Void doInBackground(Integer... integers) {
            sum = (int) mAsyncTaskDao.getAllPlayersofChamp2(integers[0]);
            return null;
        }
        @Override
        protected void onPostExecute (Void aVoid){
            super.onPostExecute(aVoid);
            if(sum==0){
                Toast.makeText(getApplication(),"problem sum=0", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplication(),"sum = kati ="+sum, Toast.LENGTH_LONG).show();
            }
        }
    }
}
