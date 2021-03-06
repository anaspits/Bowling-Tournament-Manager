package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.Date;
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

    @TypeConverters(Converters.class)
    @ColumnInfo(name = "updated_at") //tou round_detail
    private Date round_updated_date;


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

    public Date getRound_updated_date() {
        return round_updated_date;
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

    public void setRound_updated_date(Date round_updated_date) {
        this.round_updated_date = round_updated_date;
    }

    public PlayerandGames(@NonNull String participant_uuid, String firstName, String lastName, String sex, int score, @NonNull String round_uuid, int froundid, int games, int first, int second, int third, int hdcp, float bowlAvg, Date round_updated_date) {
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
        this.round_updated_date = round_updated_date;
    }

    public ArrayList<Integer> calcSetAndGamesStat(List<PlayerandGames> rds, Round r, int i, int game, int gamebegining, int set, int setbegining, int pos, int pos2, int pos3, int pos4) {
        ArrayList<Integer> scoreandpos = new ArrayList<>();

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
        if (setbegining <= rds.get(i).getScore()) {
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

            //set antrwn/gynaikwn aftou tou gyrou
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

    //na svisw
    public ArrayList<ArrayList> findPlayersDrawforStat(List<PlayerandGames> rds, Round r,int i, int game,  int gamebegining, int set, int setbegining, int pos, int pos2, int pos3, int pos4) {
        ArrayList<ArrayList> draws = new ArrayList<>();
        System.out.println("j->i "  + i);
        //vriskw an uparxou isopalies
        ArrayList<Integer> draw_gamebegining = new ArrayList<>();
        ArrayList<Integer> draw_setbegining = new ArrayList<>();
        ArrayList<Integer> draw_game = new ArrayList<>();
        ArrayList<Integer> draw_set = new ArrayList<>();

            //gia to paixnidi andrwn/gynaikwn ap'arxhs
            if (i != pos) { //H' !rds.get(i).getParticipant_uuid().equals(rds.get(pos).getParticipant_uuid())
                if (gamebegining == rds.get(i).getFirst()) {
                    //  draw_gamebegining.add(rds.get(i).getFirst());
                    draw_gamebegining.add(i);
                }
                if (gamebegining == rds.get(i).getSecond()) {
                    //  draw_gamebegining.add(rds.get(i).getSecond());
                    draw_gamebegining.add(i);
                }
                if (gamebegining == rds.get(i).getThird()) {
                    // draw_gamebegining.add(rds.get(i).getThird());
                    draw_gamebegining.add(i);
                }
            }

            //set ap'arxhs
            if (pos3 != i) {
                if (setbegining == rds.get(i).getScore()) {
                    //draw_setbegining.add(rds.get(i).getThird());
                    draw_setbegining.add(i);
                }
            }

            if (rds.get(i).getFroundid() == r.getFroundid()) {
                //paixnidi aftou tou gyrou
                if (pos2 != i) {
                    if (game == rds.get(i).getFirst()) {
                        draw_game.add(i);
                    }
                    if (game == rds.get(i).getSecond()) {
                        draw_game.add(i);
                    }
                    if (game == rds.get(i).getThird()) {
                        draw_game.add(i);
                    }
                }

                //set antrwn aftou tou gyrou
                if (pos4 != i) {
                    if (set == rds.get(i).getScore()) {
                        draw_set.add(i);
                    }
                }
            }
        draws.add(draw_game); //0
        draws.add(draw_gamebegining); //1
        draws.add(draw_set); //2
        draws.add(draw_setbegining); //3
        return draws;
    }

    public int drawforGameBegining(List<PlayerandGames> rds, int i,int gamebegining, int pos) {
    int draw_gamebegining=-1; //h 8esh tou antistoixou paixth pou exei isopalia me ton allon -vazw -1 etsi wste an den uparxei paixtis me isopalia na epistrefei -1 kai oxi px 0 pou 8a mporoyse na einai 8esi sto rds

        //gia to paixnidi andrwn/gynaikwn ap'arxhs
        if (i != pos) { //H' !rds.get(i).getParticipant_uuid().equals(rds.get(pos).getParticipant_uuid())
            if (gamebegining == rds.get(i).getFirst()) {
                draw_gamebegining=i;
            }
            if (gamebegining == rds.get(i).getSecond()) {
                draw_gamebegining=i;
            }
            if (gamebegining == rds.get(i).getThird()) {
                draw_gamebegining=i;
            }
        }
        System.out.println("draw "+draw_gamebegining);
        return draw_gamebegining;
    }

    public int drawforGame(List<PlayerandGames> rds,Round r, int i,int game, int pos2) {
        int draw_game=-1; //h 8esh tou antistoixou paixth pou exei isopalia me ton allon -vazw -1 etsi wste an den uparxei paixtis me isopalia na epistrefei -1 kai oxi px 0 pou 8a mporoyse na einai 8esi sto rds

        if (rds.get(i).getFroundid() == r.getFroundid()) {
            //paixnidi aftou tou gyrou
            if (pos2 != i) {
                if (game == rds.get(i).getFirst()) {
                    draw_game = i;
                }
                if (game == rds.get(i).getSecond()) {
                    draw_game = i;
                }
                if (game == rds.get(i).getThird()) {
                    draw_game = i;
                }
            }
        }
        System.out.println("draw "+draw_game);
        return draw_game;
    }

    public int drawforSetBegining(List<PlayerandGames> rds, int i,int setbegining, int pos3) {
        int draw_setbegining=-1; //h 8esh tou antistoixou paixth pou exei isopalia me ton allon -vazw -1 etsi wste an den uparxei paixtis me isopalia na epistrefei -1 kai oxi px 0 pou 8a mporoyse na einai 8esi sto rds

//set ap'arxhs
        if (pos3 != i) {
            if (setbegining == rds.get(i).getScore()) {
                //draw_setbegining.add(rds.get(i).getThird());
                draw_setbegining=i;
            }
        }
        System.out.println("draw "+draw_setbegining);
        return draw_setbegining;
    }

    public int drawforSet(List<PlayerandGames> rds,Round r, int i,int set, int pos4) {
        int draw_set=-1; //h 8esh tou antistoixou paixth pou exei isopalia me ton allon -vazw -1 etsi wste an den uparxei paixtis me isopalia na epistrefei -1 kai oxi px 0 pou 8a mporoyse na einai 8esi sto rds

        if (rds.get(i).getFroundid() == r.getFroundid()) {
            //set antrwn aftou tou gyrou
            if (pos4 != i) {
                if (set == rds.get(i).getScore()) {
                    draw_set=i;
                }
            }
        }
        System.out.println("draw "+draw_set);
        return draw_set;
    }

}