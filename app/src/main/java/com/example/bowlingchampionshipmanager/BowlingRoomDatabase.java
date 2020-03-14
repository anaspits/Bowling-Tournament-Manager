package com.example.bowlingchampionshipmanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Test_table.class, version = 1,exportSchema = false)
public abstract class BowlingRoomDatabase extends RoomDatabase {

    public  abstract BowlingDao bowlingDao();

    private static volatile BowlingRoomDatabase BowlingRoomInstance;

    static BowlingRoomDatabase getDatabase(final Context context) {
        if (BowlingRoomInstance == null) {
            synchronized (BowlingRoomDatabase.class) {
                if (BowlingRoomInstance == null) {
                    BowlingRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            BowlingRoomDatabase.class, "test1")
                            .build();
                }
            }
        }
        return BowlingRoomInstance;
    }
}
