package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

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
    private String participantID;

    @ColumnInfo(name="score") //tou participant
    private int score;

   /* public int getSys_roundDetailID() {
        return sys_roundDetailID;
    } */

    @NonNull
    public String getRound_uuid() {
        return round_uuid;
    }

    public String getParticipantID() {
        return participantID;
    }

    public int getScore() {
        return score;
    }

   /* public void setSys_roundDetailID(int sys_roundDetailID) {
        this.sys_roundDetailID = sys_roundDetailID;
    } */

    public void setRound_uuid(String round_uuid) {
        this.round_uuid = round_uuid;
    }

    public void setParticipantID(String participantID) {
        this.participantID = participantID;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Round_detail(String round_uuid, String participantID, int score) {
        this.round_uuid = round_uuid;
        this.participantID = participantID;
        this.score = score;
        //todo: mhpws 8elei k ton champid?
    }

}
