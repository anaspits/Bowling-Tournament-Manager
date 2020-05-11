package com.example.bowlingchampionshipmanager;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {Participant.class, Team.class,Championship.class,Round.class,Team_detail.class,Championship_detail.class,Round_detail.class,Pins_points.class,HDCPparameters.class}, version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class BowlingRoomDatabase extends RoomDatabase {

    public  abstract BowlingDao bowlingDao();
    public  abstract TeamDao teamDao();
    public  abstract ChampDao champDao();
    public  abstract Team_detailDao team_detailDao();
    public  abstract Championship_detailDao championship_detailDao();
    public  abstract RoundDao roundDao();
    public  abstract Round_detailDao rdDao();
    public  abstract Pins_pointsDao ppDao();


    private static volatile BowlingRoomDatabase BowlingRoomInstance;

   /* public synchronized static BowlingRoomDatabase getInstance(final Context context){
        if(BowlingRoomInstance)
    }*/

    static BowlingRoomDatabase getDatabase(final Context context) {
        if (BowlingRoomInstance == null) {
            synchronized (BowlingRoomDatabase.class) {
                if (BowlingRoomInstance == null) {
                    BowlingRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            BowlingRoomDatabase.class, "test")
                            .addCallback(new RoomDatabase.Callback(){
                                @Override
                                public void onCreate (@NonNull SupportSQLiteDatabase db){
                                    super.onCreate(db);
                                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            Participant p = new Participant(0, "blind", "BLIND", "", 0, 0, null, 0, "", 1);
                                            getDatabase(context).bowlingDao().insert(p);
                                        }
                                    });
                                }
                            })
                            .build();

                }
            }
        }
        return BowlingRoomInstance;
    }

}
