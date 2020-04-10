package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "round_detail",primaryKeys = {"sys_roundID","participant_uuid"}, foreignKeys = {
        @ForeignKey(entity = Round.class,
                parentColumns = "sys_roundID",
                childColumns = "sys_roundID"),
        @ForeignKey(entity = Participant.class,
                parentColumns = "participant_uuid",
                childColumns = "participant_uuid")
}, indices= {
        @Index(name="index_roundID", value="sys_roundID"),
        @Index(name="index_round_participantID", value="participant_uuid")
})
public class Round_detail  implements Serializable {
   /* @PrimaryKey(autoGenerate = true)
    @NonNull
    int sys_roundDetailID; */

    @ColumnInfo(name="sys_roundID")
    @NonNull
    private int sys_raoundID;

    @ColumnInfo(name="participant_uuid")
    @NonNull
    private String participantID;

    @ColumnInfo(name="score")
    private int score;

   /* public int getSys_roundDetailID() {
        return sys_roundDetailID;
    } */

    public int getSys_raoundID() {
        return sys_raoundID;
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

    public void setSys_raoundID(int sys_raoundID) {
        this.sys_raoundID = sys_raoundID;
    }

    public void setParticipantID(String participantID) {
        this.participantID = participantID;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Round_detail(int sys_raoundID, String participantID, int score) {
        this.sys_raoundID = sys_raoundID;
        this.participantID = participantID;
        this.score = score;
    }

}
