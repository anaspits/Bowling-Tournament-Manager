package com.example.bowlingchampionshipmanager;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BowlingViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private BowlingDao bowlingDao;
    private BowlingRoomDatabase bDB;

    private LiveData<List<Participant>> mAllNotes;

    public BowlingViewModel(@NonNull Application application) {

        super(application);

        bDB= BowlingRoomDatabase.getDatabase(application);
        bowlingDao = bDB.bowlingDao();
        mAllNotes = bowlingDao.getAllBowls();
    }


    public  void insert (Participant t){
        new InsertAsyncTask(bowlingDao).execute(t);
    }

    LiveData<List<Participant>> getAllBowls() {
        return mAllNotes;
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
}
