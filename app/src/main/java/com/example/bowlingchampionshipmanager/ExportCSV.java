package com.example.bowlingchampionshipmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

public class ExportCSV {

    public ExportCSV() {
    }

    public StringBuilder exportRoundEditScore(Championship championship, Round r, Team team1, Team team2, List<Participant> players1, List<Participant> players2, List<Round_detail> rd1, List<Round_detail> rd2, int first_sum1, int second_sum1, int third_sum1, int sum_hdcp1, int first_sum2, int second_sum2, int third_sum2, int sum_hdcp2) {
        StringBuilder data = new StringBuilder();

        data.append("Championship No.," + championship.getSys_champID() + ",UUID:," + championship.getUuid() + "\n");
        data.append("\nRound No.," + r.getFroundid() + ",Date," + Calendar.getInstance().getTime()+"\n");
        data.append("\nTeam," + team1.getTeamName() + ",Lane");
        data.append("\n,Player,HDCP,1,2,3,Sum");
        exportRoundEditScoreforTeam(team1, players1, rd1, data, first_sum1, second_sum1, third_sum1, sum_hdcp1, r);
        if (championship.getType() == 2) { //tvt
            data.append("\nVS");
            data.append("\nTeam," + team2.getTeamName() + ",Lane");
            data.append("\n,Player,HDCP,1,2,3,Sum");
            exportRoundEditScoreforTeam(team2, players2, rd2, data, first_sum2, second_sum2, third_sum2, sum_hdcp2, r);
        }
        return data;
    }

    public void exportRoundEditScoreforTeam(Team team, List<Participant> players, List<Round_detail> rd, StringBuilder data, int first_sum, int second_sum, int third_sum, int sum_hdcp, Round r) {
        for (int i = 0; i < players.size(); i++) {
            data.append("\n" + (i + 1) + "," + players.get(i).getFullName() + "," + rd.get(i).getHdcp() + "," + rd.get(i).getFirst() + "," + rd.get(i).getSecond() + "," + rd.get(i).getThird() + "," +( rd.get(i).getFirst()+rd.get(i).getSecond()+ rd.get(i).getThird()));
        }
        data.append("\n,Sum," + sum_hdcp + "," + first_sum + "," + second_sum + "," + third_sum + "," + (first_sum + second_sum + third_sum));
        if (team.getUuid().equals(r.getTeam1UUID())) {
            data.append("\n,Points," + r.getPoints1());
        } else {
            data.append("\n,Points," + r.getPoints2());
        }
    }

    public StringBuilder exportFinishedTeam(Championship championship, List<Round> rounds, Team team, List<PlayerandGames> players) {
        StringBuilder data = new StringBuilder();
        data.append("Championship No.," + championship.getSys_champID() + ",UUID:," + championship.getUuid()+",Date:,"+championship.getStart_date());
        if(championship.getStatus().equals("Finished")){
            data.append(",Finish Date:,"+championship.getEnd_date());
        }else {
            data.append(",Ongoing");
        }
        data.append("\nResults for Team," + team.getTeamName());
        data.append("\n,Round,Points,Score");
        for (int i = 0; i < rounds.size(); i++) {
            data.append("\n" + rounds.get(i).getFroundid() + "," + rounds.get(i).getTeam1ID() + "," + rounds.get(i).getPoints1() + "," + rounds.get(i).getScore1());
            data.append("\n" + ",VS " + rounds.get(i).getTeam2ID() + "," + rounds.get(i).getPoints2() + "," + rounds.get(i).getScore2());
        }
        data.append("\nFinal Score");
        if (rounds.get(rounds.size() - 1).getTeam1UUID().equals(team.getUuid())) {
            data.append("," + rounds.get(rounds.size() - 1).getScore1());
        } else {
            data.append("," + rounds.get(rounds.size() - 1).getScore2());
        }
        data.append("\n");
        data.append("\nPlayers");
        data.append("\n,Player,Average,HDCP,Games");
        for (int i = 0; i < players.size(); i++) {
            data.append("\n" +(i+1)+","+ players.get(i).getFirstName() + players.get(i).getLastName() + "," + players.get(i).getBowlAvg() + "," + players.get(i).getHdcp() + "," + players.get(i).getGames());
        }

        return data;
    }

    public StringBuilder exportFinishedChamp(Championship championship, List<Round> rounds,List<TeamandScore> teams, List<PlayerandGames> players, List<TeammatesTuple> playersandteams) {
        StringBuilder data = new StringBuilder();
        data.append("Championship No.," + championship.getSys_champID() + ",UUID:," + championship.getUuid()+",Start Date:,"+championship.getStart_date());
        if(championship.getStatus().equals("Finished")){
            data.append(",Finish Date:,"+championship.getEnd_date());
            data.append("\nWinner Team," + teams.get(0).getTeam_name() + ",Score: " + teams.get(0).getTeam_score());
        }else {
            data.append(",Status,Ongoing");
            data.append("\nWinning Team," + teams.get(0).getTeam_name() + ",Score: " + teams.get(0).getTeam_score());
        }
        data.append("\nTeam Ranking");
        data.append("\n,Team,Score");
        for (int i = 0; i < teams.size(); i++) {
            data.append("\n" + teams.get(i).getTeam_name() + ",");
            for (int j = 0; j < playersandteams.get(i).getT().size(); j++) {
                data.append(playersandteams.get(i).getT().get(j).getLastName());
                if (j != (playersandteams.get(i).getT().size() - 1)) {
                    data.append("-");
                }
                System.out.println("TEST2 team " + playersandteams.get(i).getC().getFTeamID() + " pl " + playersandteams.get(i).getT().get(j).getFullName());
            }
            data.append("," + teams.get(i).getTeam_score());
        }

        data.append("\n");
        data.append("\nRounds");
        data.append("\nRound,Team,Points,Score");
        for (int i = 0; i < rounds.size(); i++) {
            data.append("\n" + rounds.get(i).getFroundid() + "," + rounds.get(i).getTeam1ID() + "," + rounds.get(i).getPoints1() + "," + rounds.get(i).getScore1());
            data.append("\n" + ",VS " + rounds.get(i).getTeam2ID() + "," + rounds.get(i).getPoints2() + "," + rounds.get(i).getScore2());
        }

        if(championship.getStatus().equals("Finished")) {
            data.append("\n");
        data.append("\nPlayers");
            data.append("\n,Player,Average,HDCP,Games");
            for (int i = 0; i < players.size(); i++) {
                data.append("\n" + (i + 1) + "," + players.get(i).getFirstName() + players.get(i).getLastName() + "," + players.get(i).getBowlAvg() + "," + players.get(i).getHdcp() + "," + players.get(i).getGames());
            }
        }
        return data;
    }
}
