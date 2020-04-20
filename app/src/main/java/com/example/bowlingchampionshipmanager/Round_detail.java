package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import java.io.Serializable;

@Entity(tableName = "round_detail",primaryKeys = {"round_uuid","participant_uuid"}, foreignKeys = {
        @ForeignKey(entity = Round.class,
                parentColumns = "round_uuid",
                childColumns = "round_uuid"),
        @ForeignKey(entity = Participant.class,
                parentColumns = "participant_uuid",
                childColumns = "participant_uuid")
}, indices= {
        @Index(name="index_roundID", value="round_uuid"),
        @Index(name="index_round_participantID", value="participant_uuid")
})
public class Round_detail  implements Serializable {
   /* @PrimaryKey(autoGenerate = true)
    @NonNull
    int sys_roundDetailID; */

    @ColumnInfo(name="round_uuid")
    @NonNull
    private String round_uuid;

    @ColumnInfo(name="participant_uuid")
    @NonNull
    private String participant_uuid;

    @ColumnInfo(name="score") //tou participant
    private int score;
    @ColumnInfo(name="hdcp") //tou participant
    private int hdcp;
    @ColumnInfo(name="first") //tou participant
    private int first;
    @ColumnInfo(name="second") //tou participant
    private int second;
    @ColumnInfo(name="third") //tou participant
    private int third;


   /* public int getSys_roundDetailID() {
        return sys_roundDetailID;
    } */

    @NonNull
    public String getRound_uuid() {
        return round_uuid;
    }

    public String getParticipant_uuid() {
        return participant_uuid;
    }

    public int getScore() {
        return score;
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
/* public void setSys_roundDetailID(int sys_roundDetailID) {
        this.sys_roundDetailID = sys_roundDetailID;
    } */

    public int getHdcp() {
        return hdcp;
    }

    public void setRound_uuid(String round_uuid) {
        this.round_uuid = round_uuid;
    }

    public void setParticipant_uuid(String participant_uuid) {
        this.participant_uuid = participant_uuid;
    }

    public void setScore(int score) {
        this.score = score;
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

    public void setHdcp(int hdcp) {
        this.hdcp = hdcp;
    }

    public Round_detail(String round_uuid, String participant_uuid, int first, int second, int third, int hdcp) {
        this.round_uuid = round_uuid;
        this.participant_uuid = participant_uuid;
        this.first = first;
        this.second = second;
        this.third = third;
        this.hdcp=hdcp;
        //todo: mhpws 8elei k ton champid?
    }

}
