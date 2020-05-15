package com.example.bowlingchampionshipmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExportCSV {

    public ExportCSV() {
    }

    public StringBuilder exportRoundEditScore(Championship championship, Round r, Team team1, Team team2, List<Participant> players1,List<Participant> players2,List<Round_detail> rd1,List<Round_detail> rd2,int first_sum1,int second_sum1,int third_sum1,int sum_hdcp1,int first_sum2,int second_sum2,int third_sum2,int sum_hdcp2){
        StringBuilder data = new StringBuilder();

            data.append("Championship No.," + championship.fchampID + ",UUID:," + championship.getUuid()+"\n");
            data.append("\nRound No.," + r.getFroundid()+",Date"+"\n"); //todo na valw kai date kai lanes
            data.append("\nTeam," + team1.getTeamName()+",Lane");
            data.append("\n,Player,HDCP,1,2,3,Sum");
            exportRoundEditScoreforTeam(team1,players1,rd1,data,first_sum1, second_sum1, third_sum1, sum_hdcp1,r);
        if (championship.getType()==2) { //tvt
            data.append("\nVS");
            data.append("\nTeam," + team2.getTeamName()+",Lane");
            data.append("\n,Player,HDCP,1,2,3,Sum");
            exportRoundEditScoreforTeam(team2,players2,rd2,data,first_sum2, second_sum2, third_sum2, sum_hdcp2,r);
        }
        return data;
    }

    public void exportRoundEditScoreforTeam(Team team,List<Participant> players,List<Round_detail> rd,StringBuilder data,int first_sum,int second_sum,int third_sum,int sum_hdcp, Round r){
        for(int i=0;i<players.size();i++){
            data.append("\n"+(i+1)+","+players.get(i).getFullName()+","+rd.get(i).getHdcp()+","+rd.get(i).getFirst()+","+rd.get(i).getSecond()+","+rd.get(i).getThird()+","+rd.get(i).getScore());
        }
        data.append("\n,Sum," +sum_hdcp+","+first_sum+","+second_sum+","+third_sum+","+(first_sum+second_sum+third_sum));
        if(team.getUuid().equals(r.getTeam1UUID())){
         data.append("\n,,Points,"+r.getPoints1());
        }else {
            data.append("\n,,Points,"+r.getPoints2());
        }
    }
}
