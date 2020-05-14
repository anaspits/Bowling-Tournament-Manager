package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

import java.util.ArrayList;
import java.util.List;

class PlayerandGames {
    @ColumnInfo(name = "participant_uuid")
    @NonNull
    private String participant_uuid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "sex")
    String sex;

    @ColumnInfo(name = "round_uuid")
    @NonNull
    private String round_uuid;

    @ColumnInfo(name = "score") //tou participant sto sugkekrimeno round (sunolo korinwn)
    private int score;

    @ColumnInfo(name = "avg")
    private float bowlAvg;

    @ColumnInfo(name = "hdcp")
    private int hdcp;


    @ColumnInfo(name = "froundid")
    private int froundid;

    @ColumnInfo(name = "games")
    private int games;

    @ColumnInfo(name = "first") //tou participant
    private int first;
    @ColumnInfo(name = "second") //tou participant
    private int second;
    @ColumnInfo(name = "third") //tou participant
    private int third;


    @NonNull
    public String getParticipant_uuid() {
        return participant_uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public float getBowlAvg() {
        return bowlAvg;
    }

    public int getHdcp() {
        return hdcp;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }

    @NonNull
    public String getRound_uuid() {
        return round_uuid;
    }

    @NonNull
    public int getFroundid() {
        return froundid;
    }

    public int getGames() {
        return games;
    }

    public String getSex() {
        return sex;
    }

    public int getScore() {
        return score;
    }

    public void setParticipant_uuid(@NonNull String participant_uuid) {
        this.participant_uuid = participant_uuid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBowlAvg(float bowlAvg) {
        this.bowlAvg = bowlAvg;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public void setHdcp(int hdcp) {
        this.hdcp = hdcp;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public void setThird(int third) {
        this.third = third;
    }

    public void setRound_uuid(@NonNull String round_uuid) {
        this.round_uuid = round_uuid;
    }

    public void setFroundid(@NonNull int froundid) {
        this.froundid = froundid;
    }

    public PlayerandGames(@NonNull String participant_uuid, String firstName, String lastName, String sex, int score, @NonNull String round_uuid, int froundid, int games, int first, int second, int third, int hdcp, float bowlAvg) {
        this.participant_uuid = participant_uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bowlAvg = bowlAvg;
        this.hdcp = hdcp;
        this.round_uuid = round_uuid;
        this.froundid = froundid;
        this.games = games;
        this.first = first;
        this.second = second;
        this.third = third;
        this.sex = sex;
        this.score = score;
    }

    public ArrayList<Integer> calcSetAndGamesStat(List<PlayerandGames> rds, Round r, int i, int game,int gamebegining,int set,int setbegining,int pos,int pos2,int pos3,int pos4) {
        ArrayList<Integer> scoreandpos=new ArrayList<>();
                //paixnidi antra/gynaika aparxhs
                if (gamebegining <= rds.get(i).getFirst()) {
                    gamebegining = rds.get(i).getFirst();
                    pos = i;
                }
                if (gamebegining <= rds.get(i).getSecond()) {
                    gamebegining = rds.get(i).getSecond();
                    pos = i;
                }
                if (gamebegining <= rds.get(i).getThird()) {
                    gamebegining = rds.get(i).getThird();
                    pos = i;
                }

                //set ap'arxhs
                if (setbegining <= rds.get(i).getScore()) { //fixme //komple
                    setbegining = rds.get(i).getScore();
                    System.out.println("mansetbeg " + setbegining + " i " + i);
                    pos3 = i;
                }

                //paixnidi aftou tou gyrou
                if (rds.get(i).getFroundid() == r.getFroundid()) {
                    if (game <= rds.get(i).getFirst()) {
                        game = rds.get(i).getFirst();
                        pos2 = i;
                    }
                    if (game <= rds.get(i).getSecond()) {
                        game = rds.get(i).getSecond();
                        pos2 = i;
                    }
                    if (game <= rds.get(i).getThird()) {
                        game = rds.get(i).getThird();
                        pos2 = i;
                    }

                    //set antrwn aftou tou gyrou
                    if (set <= rds.get(i).getScore()) {
                        set = rds.get(i).getScore();
                        pos4 = i;
                    }

                }
        scoreandpos.add(game); //0
        scoreandpos.add(gamebegining); //1
        scoreandpos.add(set); //2
        scoreandpos.add(setbegining); //3
        scoreandpos.add(pos); //4
        scoreandpos.add(pos2);//5
        scoreandpos.add(pos3); //6
        scoreandpos.add(pos4); //7
        return scoreandpos;
    }
}
