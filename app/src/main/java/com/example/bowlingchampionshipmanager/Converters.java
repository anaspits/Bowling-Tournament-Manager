package com.example.bowlingchampionshipmanager;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {


        @TypeConverter
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

/*
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

