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
    private TeamDao teamDao;
    private ChampDao champDao;
    private Team_detailDao tdDao;
    private Championship_detailDao cdDao;
    private BowlingRoomDatabase bDB;

    private LiveData<List<Participant>> mAllNotes;
   // private LiveData<List<Participant>> testNotes;
   private LiveData<List<Team>> allteams;
    private LiveData<List<Championship>> allchamp;
    long insertResult;

    public BowlingViewModel(@NonNull Application application) {

        super(application);

        bDB= BowlingRoomDatabase.getDatabase(application);
        bowlingDao = bDB.bowlingDao();
        teamDao = bDB.teamDao();
        champDao= bDB.champDao();
        tdDao=bDB.team_detailDao();
        cdDao=bDB.championship_detailDao();
        mAllNotes = bowlingDao.getAllBowls();
        allteams = teamDao.getAllTeams();
        allchamp = champDao.getAllChamp();
        //testNotes = bowlingDao.getAllPlayersofChamp(teamid);
    }

///////////////////////////////////////gia Participant
    public void insert (Participant t){
        new InsertAsyncTask(bowlingDao).execute(t);
      //  return bowlingDao.insert(t);
    }

    LiveData<List<Participant>> getAllBowls() {
        return mAllNotes;
    }

    //step 2 -> Create1Activity 157 h AddNeActivity //mhpws na to grapsw sto ediViewModel? //na to svisw
    public int getAllPlayersofChamp2(int champID) { //axristo
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
        return bowlingDao.getAllPlayersofTeamsOrdered();
    }
    public LiveData<List<Participant>> getAllPlayersofChamp(int champid) {
        return bowlingDao.getAllPlayersofChamp(champid);
    }

    public LiveData<List<Participant>> getTeammates (int teamid) {
        return bowlingDao.getTeammates(teamid);
    }

    public LiveData<List<Participant>> getAllPlayersofTeam2(int teamid, int chID) {
        return bowlingDao.getAllPlayersofTeam2(teamid,chID);
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

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask {

        public InsertAsyncTask(BowlingDao bDao) {
            super(bDao);
        }
        @Override
        protected Void doInBackground(Participant... participants) {
           // insertResult =  mAsyncTaskDao.insert(participants[0]);
            mAsyncTaskDao.insert(participants[0]);
            System.out.println("insert player");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Create1Activity.ok = "ok";
            System.out.println("twra insert player");
         /*   if(insertResult==-1){
                Toast.makeText(getApplication(), " fail ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), " insert p " + insertResult, Toast.LENGTH_SHORT).show();
            } */
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
/* na svisw
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
    } */
///////////////////////////////////////////////////

////////////////////////gia Team
public  void insert (Team t){
    new TeamInsertAsyncTask(teamDao).execute(t);
}

    LiveData<List<Team>> getAllTeams() {
        return allteams;
    }

    //update step 2 -> Create1Activity
    public void update(Team t) {
        new TeamUpdateAsyncTask(teamDao).execute(t); //tsekare pio katw
    }

    public void delete(Team t) {
        new TeamDeleteAsyncTask(teamDao).execute(t);
    }

    public LiveData<Team> getTeam(int teamID) {
        return teamDao.getTeam(teamID);
    }

    public LiveData<List<Team>> getAllTeamsofChamp(int champid) {
        return teamDao.getAllTeamsofChamp(champid);
    }

   // public List<TeammatesTuple> getTeammatesid(int teamid){
     //   return teamDao.getTeammatesid(teamid);
    //}

    public LiveData<List<ActiveChampsTuple>> getAllTeamsofChamp2( ){
    return teamDao.getAllTeamsofChamp2();
    }

    private class TeamOperationsAsyncTask extends AsyncTask<Team, Void, Void> {

        TeamDao mAsyncTaskDao;

        TeamOperationsAsyncTask(TeamDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            return null;
        }
    }
    private class TeamInsertAsyncTask extends TeamOperationsAsyncTask {

        public TeamInsertAsyncTask(TeamDao bDao) {
            super(bDao);
        }
        @Override
        protected Void doInBackground(Team... teams) {
            mAsyncTaskDao.insert(teams[0]);

            return null;
        }
    }

    private class TeamUpdateAsyncTask extends TeamOperationsAsyncTask {

        TeamUpdateAsyncTask(TeamDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Team... teams) {
            mAsyncTaskDao.update(teams[0]);
            return null;
        }
    }

    private class TeamDeleteAsyncTask extends TeamOperationsAsyncTask {

        public TeamDeleteAsyncTask(TeamDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Team... teams) {
            mAsyncTaskDao.delete(teams[0]);
            return null;
        }
    }
///////////////////////////////////////////////////

////////////////////gia Champ
public  void insert (Championship t){
    new ChampInsertAsyncTask(champDao).execute(t);
}

    //update step 2 -> Create1Activity
    public void update(Championship t) {
        new ChampUpdateAsyncTask(champDao).execute(t); //tsekare pio katw
    }

    public void delete(Championship t) {
        new ChampDeleteAsyncTask(champDao).execute(t);
    }

    LiveData<List<Championship>> getAllChamp() {
        return allchamp;
    }

    LiveData<Championship> getLastInsertChamp(){
        return champDao.getLastInsertChamp();
    }

   // public LiveData<TeammatesTuple> getTeamsid(int champid){
    //    return champDao.getTeamsid(champid);
    //}

    public LiveData<List<Integer>> getActiveChamp(){
    return champDao.getActiveChamp();
    } //axristo

    public LiveData<List<Championship>> getActiveChamp3(){
        return champDao.getActiveChamp3();
    }

    private class ChampOperationsAsyncTask extends AsyncTask<Championship, Void, Void> {

        ChampDao mAsyncTaskDao;

        ChampOperationsAsyncTask(ChampDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Championship... champs) {
            return null;
        }
    }
    private class ChampInsertAsyncTask extends ChampOperationsAsyncTask {

        public ChampInsertAsyncTask(ChampDao bDao) {
            super(bDao);
        }
        @Override
        protected Void doInBackground(Championship... champs) {
            mAsyncTaskDao.insert(champs[0]);

            return null;
        }
    }

    private class ChampUpdateAsyncTask extends ChampOperationsAsyncTask {

        ChampUpdateAsyncTask(ChampDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Championship... champs) {
            mAsyncTaskDao.update(champs[0]);
            return null;
        }
    }

    private class ChampDeleteAsyncTask extends ChampOperationsAsyncTask {

        public ChampDeleteAsyncTask(ChampDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Championship... champs) {
            mAsyncTaskDao.delete(champs[0]);
            return null;
        }
    }
//////////////////////////////////////
    ////////////////////////////////
    //gia team_detail
public  void insert (Team_detail t){
    new Team_detailInsertAsyncTask(tdDao).execute(t);
}

    //update step 2 -> Create1Activity
    public void update(Team_detail t) {
        new Team_detailUpdateAsyncTask(tdDao).execute(t); //tsekare pio katw
    }

    public void delete(Team_detail t) {
        new Team_detailUpdateAsyncTask(tdDao).execute(t);
    }

    public LiveData<List<Team_detail>> getAllTeam_detail(){
           return tdDao.getAllTeam_detail();
    }

    private class Team_detailOperationsAsyncTask extends AsyncTask<Team_detail, Void, Void> {

        Team_detailDao mAsyncTaskDao;

        Team_detailOperationsAsyncTask(Team_detailDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Team_detail... tds) {
            return null;
        }
    }
    private class Team_detailInsertAsyncTask extends Team_detailOperationsAsyncTask {

        public Team_detailInsertAsyncTask(Team_detailDao bDao) {
            super(bDao);
        }
        @Override
        protected Void doInBackground(Team_detail... tds) {
            mAsyncTaskDao.insert(tds[0]);
            Log.i(TAG,"td insert");
            return null;
        }
    }

    private class Team_detailUpdateAsyncTask extends Team_detailOperationsAsyncTask {

        Team_detailUpdateAsyncTask(Team_detailDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Team_detail... tds) {
            mAsyncTaskDao.update(tds[0]);
            return null;
        }
    }

    private class Team_detailDeleteAsyncTask extends Team_detailOperationsAsyncTask {

        public Team_detailDeleteAsyncTask(Team_detailDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Team_detail... tds) {
            mAsyncTaskDao.delete(tds[0]);
            return null;
        }
    }
///////////////////////////////
    ///////////////////// gia Championship Detail

    public  void insert (Championship_detail t){
        new Championship_detailInsertAsyncTask(cdDao).execute(t);

    }

    //update step 2 -> Create1Activity
    public void update(Championship_detail t) {
        new Championship_detailUpdateAsyncTask(cdDao).execute(t); //tsekare pio katw
    }

    public void delete(Championship_detail t) {
        new Championship_detailDeleteAsyncTask(cdDao).execute(t);
    }

    public LiveData<List<Championship_detail>> getAllChamp_detail(){
        return cdDao.getAllChamp_detail();
    }

    private class Championship_detailOperationsAsyncTask extends AsyncTask<Championship_detail, Void, Void> {

        Championship_detailDao mAsyncTaskDao;

        Championship_detailOperationsAsyncTask(Championship_detailDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Championship_detail... cds) {
            return null;
        }
    }
    private class Championship_detailInsertAsyncTask extends Championship_detailOperationsAsyncTask {

        public Championship_detailInsertAsyncTask(Championship_detailDao bDao) {
            super(bDao);
        }
        @Override
        protected Void doInBackground(Championship_detail... cds) {
            mAsyncTaskDao.insert(cds[0]);
            Log.i(TAG, "insert cd");
            return null;
        }
    }

    private class Championship_detailUpdateAsyncTask extends Championship_detailOperationsAsyncTask {

        Championship_detailUpdateAsyncTask(Championship_detailDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Championship_detail... cds) {
            mAsyncTaskDao.update(cds[0]);
            return null;
        }
    }

    private class Championship_detailDeleteAsyncTask extends Championship_detailOperationsAsyncTask {

        public Championship_detailDeleteAsyncTask(Championship_detailDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Championship_detail... cds) {
            mAsyncTaskDao.delete(cds[0]);
            return null;
        }
    }

}
