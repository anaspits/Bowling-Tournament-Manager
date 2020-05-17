package com.example.bowlingchampionshipmanager;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "team_detail",primaryKeys = {"team_uuid","participant_uuid"}, foreignKeys = {
        @ForeignKey(entity = Team.class,
                parentColumns = "team_uuid",
                childColumns = "team_uuid"),
        @ForeignKey(entity = Participant.class,
                parentColumns = "participant_uuid",
                childColumns = "participant_uuid")
}, indices= {
        @Index(name="index_td_teamID", value="team_uuid"),
        @Index(name="index_participantID", value="participant_uuid") //todo mhpws na valw kai to champ
}  )
public class Team_detail {
/*    @PrimaryKey(autoGenerate = true)
    @NonNull
    int sys_teamDetailID;*/

    @ColumnInfo(name="team_uuid")
    @NonNull
    private String teamID;

    @ColumnInfo(name="participant_uuid")
    @NonNull
    private String participantID;

 /*   public String getSys_teamDetailID() {
        return sys_teamDetailID;
    }*/

    public String getTeamID() {
        return teamID;
    }

    public String getParticipantID() {
        return participantID;
    }

/*    public void setSys_teamDetailID(String sys_teamDetailID) {
        this.sys_teamDetailID = sys_teamDetailID;
    }*/
    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public void setParticipantID(String participantID) {
        this.participantID = participantID;
    }

    public Team_detail(String teamID, String participantID) {
        this.teamID = teamID;
        this.participantID =  participantID;
    }
}
