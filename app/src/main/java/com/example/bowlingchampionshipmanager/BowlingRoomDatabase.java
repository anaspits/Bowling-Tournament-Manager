package com.example.bowlingchampionshipmanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Participant.class, Team.class,Championship.class,Round.class,HDCPparameters.class}, version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class BowlingRoomDatabase extends RoomDatabase {

    public  abstract BowlingDao bowlingDao();
    public  abstract TeamDao teamDao();
    public  abstract ChampDao champDao();

    private static volatile BowlingRoomDatabase BowlingRoomInstance;

    static BowlingRoomDatabase getDatabase(final Context context) {
        if (BowlingRoomInstance == null) {
            synchronized (BowlingRoomDatabase.class) {
                if (BowlingRoomInstance == null) {
                    BowlingRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            BowlingRoomDatabase.class, "Bowling")
                            .build();
                }
            }
        }
        return BowlingRoomInstance;
    }
}
