package com.example.bowlingchampionshipmanager;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class BowlingViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private BowlingDao bowlingDao;
    private TeamDao teamDao;
    private ChampDao champDao;
    private Team_detailDao tdDao;
    private Championship_detailDao cdDao;
    private RoundDao rDao;
    private Round_detailDao rdDao;
    private Pins_pointsDao ppDao;
    private BowlingRoomDatabase bDB;

    private LiveData<List<Participant>> mAllNotes;
   // private LiveData<List<Participant>> testNotes;
   private LiveData<List<Team>> allteams;
    private LiveData<List<Championship>> allchamp;
    private LiveData<List<Round>> allrounds;
    private LiveData<List<Round_detail>> allrds;
    private LiveData<List<Pins_points>> allpps;
    long insertResult=-1;

    private MutableLiveData<Long> dbId = new MutableLiveData<>();

    public BowlingViewModel(@NonNull Application application) {

        super(application);

        bDB= BowlingRoomDatabase.getDatabase(application);
        bowlingDao = bDB.bowlingDao();
        teamDao = bDB.teamDao();
        champDao= bDB.champDao();
        tdDao=bDB.team_detailDao();
        cdDao=bDB.championship_detailDao();
        rDao=bDB.roundDao();
        rdDao=bDB.rdDao();
        ppDao=bDB.ppDao();
        mAllNotes = bowlingDao.getAllBowls();
        allteams = teamDao.getAllTeams();
        allchamp = champDao.getAllChamp();
        allrounds = rDao.getAllRound();
        allrds = rdDao.getAllRound_detail();
        allpps= ppDao.getAllPins_points();
        //testNotes = bowlingDao.getAllPlayersofChamp(teamid);
    }

///////////////////////////////////////gia Participant
  /*  public Long insert (Participant t){
      PInsertAsyncTask i =  new PInsertAsyncTask(bowlingDao);
      i.execute(t);
     Long a= i.in;
      // return bowlingDao.insert(t);
        return a;
    }*/
  //long insert
/* public void  insert(Participant p){
      insertAsync(p);
  }
  private void insertAsync(final Participant p){
      new Thread(new Runnable() {
          @Override
          public void run() {
              Long id= bowlingDao.insert(p);
              dbId.postValue(id);
          }
      }).start();
  }
  public LiveData<Long> insertP(Participant t){
      return dbId;
  } */

    public void  insert(Participant t){  new InsertAsyncTask(bowlingDao).execute(t);  }
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


    public LiveData<List<Participant>> getAllPlayersofTeam3(String teamid, String chID) {
        return bowlingDao.getAllPlayersofTeam3(teamid, chID);
    }

    public LiveData<List<Participant>> getAllPlayersofOpositeTeam(String teamid, String chID) {
        return bowlingDao.getAllPlayersofOpositeTeam(teamid, chID);
    }

    public LiveData<List<Participant>> getParticipantByName(String fn, String ln){
        return bowlingDao.getParticipantByName(fn, ln);
    }

    LiveData<List<Participant>> getAllPlayersofChamp( String chID){
        return bowlingDao.getAllPlayersofChamp(chID);
    }

    private class PInsertAsyncTask extends AsyncTask<Participant, Void, Long> {

        BowlingDao mAsyncTaskDao;
        Long in;

        public PInsertAsyncTask(BowlingDao bDao) {
            mAsyncTaskDao=bDao;
        }
        @Override
        protected Long doInBackground(Participant... participants) {
           // insertResult =  mAsyncTaskDao.insert(participants[0]);
           // in=insertResult;
            // mAsyncTaskDao.insert(participants[0]);
            System.out.println("insert player");
            return insertResult;
        }

        @Override
        protected void onPostExecute(Long a) {
            super.onPostExecute(a);

            System.out.println("twra insert player");
            if(insertResult==-1){
                Toast.makeText(getApplication(), " fail ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplication(), " insert p " + insertResult, Toast.LENGTH_SHORT).show();
            }
            in=insertResult;
        }
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
//            insertResult =  mAsyncTaskDao.insert(participants[0]);
            mAsyncTaskDao.insert(participants[0]);
            System.out.println("insert player");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

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
            System.out.println("update p "+participants[0].getParticipantID());
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

    public Team getTeam2(int teamID) {
        return teamDao.getTeam2(teamID);
    }

    public LiveData<Team> getTeamfromUUID(String teamID) {
        return teamDao.getTeamfromUUID(teamID);
    }

    public LiveData<List<Team>> getAllTeamsofChamp(int champid) {
        return teamDao.getAllTeamsofChamp(champid);
    }

   // public List<TeammatesTuple> getTeammatesid(int teamid){
     //   return teamDao.getTeammatesid(teamid);
    //}

    public LiveData<List<ActiveChampsTuple>> getAllTeamsofChamp2( String champuuid){
    return teamDao.getAllTeamsofChamp2(champuuid);
    }
    public LiveData<List<Team>>getAllTeamsofChamp3( String champid){
        return teamDao.getAllTeamsofChamp3(champid);
    }

    public LiveData<List<Team>> getActiveTeamsofChamp(String champid){
        return teamDao.getActiveTeamsofChamp(champid);
    }
    LiveData<List<TeammatesTuple>>  getAllPlayersofTeam3(String s){
        return teamDao.getAllPlayersofTeam3(s);
    }

    LiveData<List<TeamandScore>> getRankedAllTeamsofChamp( String champid){
        return teamDao.getRankedAllTeamsofChamp(champid);
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

    LiveData<Championship> getChampUUID ( String champUUID) {
        return champDao.getChampUUID(champUUID);
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

    public LiveData<List<Championship>> getFinChamps(){
        return champDao.getFinChamps();
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
            System.out.println("update ch "+champs[0].getSys_champID());
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

    public LiveData<List<Championship_detail>>getChamp_detailofChamp(String chid){
        return cdDao.getChamp_detailofChamp(chid);
    }

    public LiveData<List<Championship_detail>>getChamp_detailofChampofFinnishedTeams(String chid){
        return cdDao.getChamp_detailofChampofFinnishedTeams(chid);
    }

    public LiveData<Championship_detail> getChamp_detailofTeamandChamp(String tid, String chid){
        return cdDao.getChamp_detailofTeamandChamp(tid,chid);
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

    /////////////////////////////////////
///////////////////////////////////////
///////////gia Round

    public  void insert (Round t){
        new RoundInsertAsyncTask(rDao).execute(t);
    }

    //update step 2 -> Create1Activity
    public void update(Round t) {
        new RoundUpdateAsyncTask(rDao).execute(t); //tsekare pio katw
    }

    public void delete(Round t) {
        new RoundDeleteAsyncTask(rDao).execute(t);
    }

    LiveData<List<Round>> getAllRound() {
        return allrounds;
    }

    LiveData<List<Round>> getAllRoundofChamp(String champuuid){
        return rDao.getAllRoundofChamp(champuuid);
    }

    LiveData<List<Round>> getRoundsofTeam( String teamuuid, String champuuid){
        return rDao.getRoundsofTeam(teamuuid,champuuid);
    }

    LiveData<List<Round>> getAllRoundsofTeam( String teamuuid,String champuuid){
        return rDao.getAllRoundsofTeam(teamuuid,champuuid);
    }

    LiveData<Round> getCurrentRoundofTeam(String teamuuid, String champuuid){
        return rDao.getCurrentRoundofTeam(teamuuid,champuuid);
    }

     LiveData<List<Round>> getNextRoundofTeamofChamp(String teamuuid, String champuuid){
        return rDao.getNextRoundofTeamofChamp(teamuuid,champuuid);
    }

    private class RoundOperationsAsyncTask extends AsyncTask<Round, Void, Void> {

        RoundDao mAsyncTaskDao;

        RoundOperationsAsyncTask(RoundDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Round... rounds) {
            return null;
        }
    }
    private class RoundInsertAsyncTask extends RoundOperationsAsyncTask {

        public RoundInsertAsyncTask(RoundDao bDao) {
            super(bDao);
        }
        @Override
        protected Void doInBackground(Round... rounds) {
            mAsyncTaskDao.insert(rounds[0]);

            return null;
        }
    }

    private class RoundUpdateAsyncTask extends RoundOperationsAsyncTask {

        RoundUpdateAsyncTask(RoundDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Round... rounds) {
            mAsyncTaskDao.update(rounds[0]);
            return null;
        }
    }

    private class RoundDeleteAsyncTask extends RoundOperationsAsyncTask {

        public RoundDeleteAsyncTask(RoundDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Round... rounds) {
            mAsyncTaskDao.delete(rounds[0]);
            return null;
        }
    }

    /////////////////////////////////////
///////////////////////////////////////
///////////gia Round_detail

    public  void insert (Round_detail t){
        new Round_detailInsertAsyncTask(rdDao).execute(t);
    }

    //update step 2 -> Create1Activity
    public void update(Round_detail t) {
        new Round_detailUpdateAsyncTask(rdDao).execute(t); //tsekare pio katw
    }

    public void delete(Round_detail t) {
        new Round_detailDeleteAsyncTask(rdDao).execute(t);
    }

    LiveData<List<Round_detail>> getAllRound_detail() {
        return allrds;
    }

    LiveData<Round_detail> getRound_detail(String pid, String rid){
        return rdDao.getRound_detail(pid,rid);
    }

    LiveData<List<Round_detail>> getallAllRound_detailofplayer(String pid){
        return rdDao.getallAllRound_detailofplayer(pid);
    }

    LiveData<List<PlayerandGames>> getPlayerScoreGamesofPreviousRounds( String rid,String pid, int frid){
        return rdDao.getPlayerScoreGamesofPreviousRounds(rid,pid,frid);
    }

    private class Round_detailOperationsAsyncTask extends AsyncTask<Round_detail, Void, Void> {

        Round_detailDao mAsyncTaskDao;

        Round_detailOperationsAsyncTask(Round_detailDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Round_detail... rds) {
            return null;
        }
    }
    private class Round_detailInsertAsyncTask extends Round_detailOperationsAsyncTask {

        public Round_detailInsertAsyncTask(Round_detailDao bDao) {
            super(bDao);
        }
        @Override
        protected Void doInBackground(Round_detail... rds) {
            mAsyncTaskDao.insert(rds[0]);

            return null;
        }
    }

    private class Round_detailUpdateAsyncTask extends Round_detailOperationsAsyncTask {

        Round_detailUpdateAsyncTask(Round_detailDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Round_detail... rds) {
            mAsyncTaskDao.update(rds[0]);
            return null;
        }
    }

    private class Round_detailDeleteAsyncTask extends Round_detailOperationsAsyncTask {

        public Round_detailDeleteAsyncTask(Round_detailDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Round_detail... rds) {
            mAsyncTaskDao.delete(rds[0]);
            return null;
        }
    }

//////////////////////////////
    ////////////////////////Pins_points

    public  void insert (Pins_points t){
        new Pins_pointsInsertAsyncTask(ppDao).execute(t);
    }

    public void update(Pins_points t) {
        new Pins_pointsUpdateAsyncTask(ppDao).execute(t); //tsekare pio katw
    }

    public void delete(Pins_points t) {
        new Pins_pointsDeleteAsyncTask(ppDao).execute(t);
    }

    LiveData<List<Pins_points>> getAllPins_points() {
        return allpps;
    }

    LiveData<List<Pins_points>> getPins_pointsofChamp(String cid){
        return ppDao.getPins_pointsofChamp(cid);
    }

    private class Pins_pointsOperationsAsyncTask extends AsyncTask<Pins_points, Void, Void> {

        Pins_pointsDao mAsyncTaskDao;

        Pins_pointsOperationsAsyncTask(Pins_pointsDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Pins_points... rds) {
            return null;
        }
    }
    private class Pins_pointsInsertAsyncTask extends Pins_pointsOperationsAsyncTask {

        public Pins_pointsInsertAsyncTask(Pins_pointsDao bDao) {
            super(bDao);
        }
        @Override
        protected Void doInBackground(Pins_points... rds) {
            mAsyncTaskDao.insert(rds[0]);

            return null;
        }
    }

    private class Pins_pointsUpdateAsyncTask extends Pins_pointsOperationsAsyncTask {

        Pins_pointsUpdateAsyncTask(Pins_pointsDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Pins_points... rds) {
            mAsyncTaskDao.update(rds[0]);
            return null;
        }
    }

    private class Pins_pointsDeleteAsyncTask extends Pins_pointsOperationsAsyncTask {

        public Pins_pointsDeleteAsyncTask(Pins_pointsDao bDao) {
            super(bDao);
        }

        @Override
        protected Void doInBackground(Pins_points... rds) {
            mAsyncTaskDao.delete(rds[0]);
            return null;
        }
    }

}
