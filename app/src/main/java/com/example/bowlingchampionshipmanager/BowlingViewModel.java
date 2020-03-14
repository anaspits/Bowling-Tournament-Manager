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

    private LiveData<List<Test_table>> mAllNotes;

    public BowlingViewModel(@NonNull Application application) {

        super(application);

        bDB= BowlingRoomDatabase.getDatabase(application);
        bowlingDao = bDB.bowlingDao();
        mAllNotes = bowlingDao.getAllBowls();
    }


    public  void insert (Test_table t){
        new InsertAsyncTask(bowlingDao).execute(t);
    }

    LiveData<List<Test_table>> getAllBowls() {
        return mAllNotes;
    }

    //update step 2 -> Create1Activity
    public void update(Test_table test_table) {
        new UpdateAsyncTask(bowlingDao).execute(test_table); //tsekare pio katw
    }

    public void delete(Test_table test_table) {
        new DeleteAsyncTask(bowlingDao).execute(test_table);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    private class OperationsAsyncTask extends AsyncTask<Test_table, Void, Void> {

       BowlingDao mAsyncTaskDao;

        OperationsAsyncTask(BowlingDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Test_table... test_tables) {
            return null;
        }
    }
    private class InsertAsyncTask extends OperationsAsyncTask {

        public InsertAsyncTask(BowlingDao bDao) {
            super(bDao);
        }
        @Override
        protected Void doInBackground(Test_table... test_tables) {
            mAsyncTaskDao.insert(test_tables[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(BowlingDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Test_table... test_tables) {
            mAsyncTaskDao.update(test_tables[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationsAsyncTask {

        public DeleteAsyncTask(BowlingDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Test_table... test_tables) {
            mAsyncTaskDao.delete(test_tables[0]);
            return null;
        }
    }
}
