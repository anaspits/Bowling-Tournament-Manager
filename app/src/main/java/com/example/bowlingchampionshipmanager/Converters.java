package com.example.bowlingchampionshipmanager;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
//import java.sql.Date;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Converters {


       /* @TypeConverter
        public static String fromArrayList(ArrayList<Integer> teamatesid) {
        Gson gson = new Gson();
        String json = gson.toJson(teamatesid);
        return json;
        }
        @TypeConverter
        public static ArrayList<Integer> toArrayList(String value) {
            Type listType = new TypeToken<ArrayList<Integer>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }
*/
        //gia java.util.date
        @TypeConverter
        public static Date fromTimestamp(Long value) {
                return value==null ? null : new Date(value);
        }
        @TypeConverter
        public static Long dateToTimestamp (Date date) {
                return date == null ? null : date.getTime();
        }

   /* //gia offsetdatetime
        private static DateTimeFormatter formater = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        @TypeConverter
        public static OffsetDateTime fromTimestamp(String value) {
            return value==null ? null : formater.parse(value, OffsetDateTime::from);
        }
    @TypeConverter
    public static String offdateToTimestamp (OffsetDateTime date) {
        return date == null ? null : date.format(formater);
    } */



/*axristo
    @TypeConverter // note this annotation
    public String fromParticipantList(ArrayList<Participant> teamates) {
        if (teamates == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Participant>>() {
        }.getType();
        String json = gson.toJson(teamates, type);
        return json;
    }

    @TypeConverter // note this annotation
    public ArrayList<Participant> toParticipantList(String teamatesString) {
        if (teamatesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Participant>>() {
        }.getType();
        ArrayList<Participant> productCategoriesList = gson.fromJson(teamatesString, type);
        return productCategoriesList;
    }
*/
}

