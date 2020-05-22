package com.example.bowlingchampionshipmanager;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "pins_points", foreignKeys = {
                @ForeignKey(entity = Championship.class,
                        parentColumns = "champ_uuid",
                        childColumns = "champ_uuid",
                        onDelete = CASCADE)
        }, indices= {
                @Index(name="index_pp_champID", value="champ_uuid")
        }  )
public class Pins_points { //max pins = x points : px mexri kai ta 200 pins = 2 pontoi
    @PrimaryKey
    @NonNull
    private String pins_uuid;

    @ColumnInfo(name="champ_uuid")
    @NonNull
    private String champ_uuid;

    @ColumnInfo(name="pins")
    private int pins; //mexri poses korines

    @ColumnInfo(name="points")
    private int points; //posoi va8moi

    @NonNull
    public String getPins_uuid() {
        return pins_uuid;
    }

    @NonNull
    public String getChamp_uuid() {
        return champ_uuid;
    }

    public int getPins() {
        return pins;
    }

    public int getPoints() {
        return points;
    }

    public void setPins_uuid(@NonNull String pins_uuid) {
        this.pins_uuid = pins_uuid;
    }

    public void setChamp_uuid(@NonNull String champ_uuid) {
        this.champ_uuid = champ_uuid;
    }

    public void setPins(int pins) {
        this.pins = pins;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Pins_points(@NonNull String pins_uuid, @NonNull String champ_uuid, int pins, int points) {
        this.pins_uuid = pins_uuid;
        this.champ_uuid = champ_uuid;
        this.pins = pins;
        this.points = points;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<Pins_points> createPins_pointsList(BowlingViewModel bowlingViewModel, InputStream inputStream , String line, String cvsSplitBy,String ch,ArrayList<Pins_points> pp)throws IOException {
        //try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
        BufferedReader br =
                new BufferedReader(new InputStreamReader(
                        inputStream));

        String [] input = null;
        int pins = 0;
        int points = 0;

        int i = 0;
        while ((line = br.readLine()) != null){
            // use comma as seperator

            //get input
            input = line.split(cvsSplitBy);
//                System.out.println(Arrays.toString(input));

            //get pins
            pins = Integer.parseInt(input[0]);

            //get points
            points = Integer.parseInt(input[1]);
            System.out.println("id: " + i + ", pins: " +  pins + ", points: " + points );

           String uuid = UUID.randomUUID().toString();
            Long ts = System.currentTimeMillis();
            String timestamp = ts.toString();

            //Date date = (Date) Calendar.getInstance().getTime();//fixme
            // System.out.println("time = "+date);
            Pins_points p = new Pins_points(uuid,ch,pins, points);
            pp.add(p);
            bowlingViewModel.insert(p);
            i++;

        }

        // } catch(IOException e){
        //     e.printStackTrace();
        // }

        return pp;
    }
}
