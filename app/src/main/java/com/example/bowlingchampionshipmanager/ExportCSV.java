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
        data.append("Championship No.," + championship.getSys_champID() + ",UUID:," + championship.getUuid()+", Start Date:,"+championship.getStart_date());
        if(championship.getStatus().equals("Finished")){
            data.append(",Finish Date:,"+championship.getEnd_date());
        }else {
            data.append(",Ongoing");
        }
        data.append("\nResults for Team," + team.getTeamName());
        data.append("\n,Round,Points,Score");
        for (int i = 0; i < rounds.size(); i++) {
            data.append("\n" + rounds.get(i).getFroundid() + "," + rounds.get(i).getTeam1ID() + "," + rounds.get(i).getPoints1() + "," + rounds.get(i).getScore1());
            if (championship.getType() == 2) {
                data.append("\n" + ",VS " + rounds.get(i).getTeam2ID() + "," + rounds.get(i).getPoints2() + "," + rounds.get(i).getScore2());
            }
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

    public StringBuilder exportRoundTeamStat(Championship championship, List<Round> rounds,List<TeamandRoundScore> teams, List<TeammatesTuple> playersandteams) {
        StringBuilder data = new StringBuilder();
        data.append("Championship No.," + championship.getSys_champID() + ",UUID:," + championship.getUuid()+",Start Date:,"+championship.getStart_date()+"\n");
        if(rounds.size()!=0) {
            data.append(" ,Team,");

            for (int i = 0; i < rounds.size(); i++) {
                if (i == 0) {
                    data.append(rounds.get(i).getFroundid() + "H,");
                } else if (rounds.get(i).getFroundid() != rounds.get(i - 1).getFroundid()) {
                    data.append(rounds.get(i).getFroundid() + "H,");
                } else if (rounds.get(i).getFroundid() == rounds.get(i - 1).getFroundid()) {
                    System.out.println("i " + i + " fr " + rounds.get(i).getFroundid());
                    //break() H continue();
                }
            }
            data.append("Sum Points");
            if(teams.size()!=0){
                int counter = 1;
                for (int i = 0; i < teams.size(); i++) { //gia ka8e omada tou champ (me order fteamid- ara exw omada-guros1,2,3..)
                    TeamandRoundScore t = teams.get(i);
                    if (i == 0) { //gia thn prwth omada
                        data.append("\n" + counter + "," + String.valueOf(t.getTeam_name())+")");
                        for (int j = 0; j < playersandteams.get(counter-1).getT().size(); j++) { //pairnw tous paiktes ths omadas
                            System.out.println("pl size "+playersandteams.get(counter-1).getT().size());
                            data.append(playersandteams.get(counter-1).getT().get(j).getLastName());
                            if(j!=(playersandteams.get(counter-1).getT().size()-1)){
                                data.append("-");
                            }
                        }
                        counter++;
                        if (championship.getType() == 1 || championship.getType() == 4) { //pairnw ta points tou prwtou gyrou
                            data.append("," + t.getPoints1());
                        } else if (championship.getType() == 2) {
                            if (t.getTeam_uuid().equals(t.getTeam1_uuid())) {
                                data.append("," + t.getPoints1());
                            } else {
                                data.append("," + t.getPoints2());
                            }
                        }
                    } else if (i == (teams.size() - 1)) { //gia to teleftaio stoixeio (teleftaia omada-teleftaios guros ths)
                        if ( !t.getTeam_uuid().equals(teams.get(i - 1).getTeam_uuid())) { //an den einai idia omada me thn prohgoumenh (dld htan mono 1 gyros)
                            if (championship.getType() == 1 || championship.getType() == 4) { //tote kanw oti kanw sthn else, dld pairnw ta score ths prohgoumenhs omadas
                                data.append("," + teams.get(i - 1).getScore1());
                            } else if (championship.getType() == 2) {
                                if (teams.get(i - 1).getTeam_uuid().equals(teams.get(i - 1).getTeam1_uuid())) {
                                    data.append("," + teams.get(i - 1).getScore1());
                                } else {
                                    data.append("," + teams.get(i - 1).getScore2());
                                }
                            }
                            data.append("\n" + counter + "," + String.valueOf(teams.get(i).getTeam_name())+")"); //kai meta pairnw to onma k tous paiktes afths ths omadas
                            for (int j = 0; j < playersandteams.get((counter-1)).getT().size(); j++) {
                                data.append(playersandteams.get((counter-1)).getT().get(j).getLastName());
                                if(j!=(playersandteams.get((counter-1)).getT().size()-1)){
                                    data.append("-");
                                }
                            }
                        }
                            //epishw pairnw ta points afths ths omadas
                        if (championship.getType() == 1 || championship.getType() == 4) {
                            data.append("," + teams.get(i).getPoints1()); //giati +1|?
                        } else if (championship.getType() == 2) {
                            if (teams.get(i).getTeam_uuid().equals(teams.get(i).getTeam1_uuid())) {
                                data.append("," + teams.get(i).getPoints1());
                            } else {
                                data.append("," + teams.get(i).getPoints2());
                            }
                        }
                        //kai to score ths (afou einai 1 gyros mono ara einai kai o teleftaios ths)
                        if (championship.getType() == 1 || championship.getType() == 4) {
                            data.append("," + t.getScore1());
                        } else if (championship.getType() == 2) {
                            if (t.getTeam_uuid().equals(t.getTeam1_uuid())) {
                                data.append("," + t.getScore1());
                            } else {
                                data.append("," + t.getScore2());//todo na rwthsw
                            }
                        }
                    } else if ( t.getTeam_uuid().equals(teams.get(i - 1).getTeam_uuid())) { //an einai idia omada me thn prohgoumenh, shmainei oti phgame ston epomeno gyro (ths idias omadas)
                        if (championship.getType() == 1 || championship.getType() == 4) { //ara pairnoume mono ta points ths gia afton ton gyro
                            data.append("," + teams.get(i).getPoints1());
                        } else if (championship.getType() == 2) {
                            if (teams.get(i ).getTeam_uuid().equals(teams.get(i  ).getTeam1_uuid())) {
                                data.append("," + teams.get(i).getPoints1());
                            } else {
                                data.append("," + teams.get(i ).getPoints2());
                            }
                        }

                    } else { //an den einai h idia omada me thn prohgoumenh, shmainei oti teleiwsan oi guroi ths prohgoumenhs omadas
                     /*   if (championship.getType() == 1 || championship.getType() == 4) {
                            data.append("," + t.getPoints1());
                        } else if (championship.getType() == 2) {
                            if (t.getTeam_uuid().equals(t.getTeam1_uuid())) {
                                data.append("," + t.getPoints1());
                            } else {
                                data.append("," + t.getPoints2());
                            }
                        }*/
                        if (championship.getType() == 1 || championship.getType() == 4) { //opote prepei na paroume to score ths prohgoumenhs
                            data.append("," + teams.get(i - 1).getScore1());
                        } else if (championship.getType() == 2) {
                            if (teams.get(i - 1).getTeam_uuid().equals(teams.get(i - 1).getTeam1_uuid())) {
                                data.append("," + teams.get(i - 1).getScore1());
                            } else {
                                data.append("," + teams.get(i - 1).getScore2());
                            }
                        }
                        data.append("\n" + counter + "," + String.valueOf(teams.get(i).getTeam_name())+")"); //kai na perasoume se afthn pou eimaste twra (dld thn epomenh) kai na paroume to onoma kai tous paiktes ths
                        for (int j = 0; j < playersandteams.get((counter-1)).getT().size(); j++) {
                            data.append(playersandteams.get((counter-1)).getT().get(j).getLastName());
                            if(j!=(playersandteams.get((counter-1)).getT().size()-1)){
                                data.append("-");
                            }
                        }
                        counter++;
                        if (championship.getType() == 1 || championship.getType() == 4) { //kai ta points ths (gia ton prwto ths gyro)
                            data.append("," + teams.get(i).getPoints1());
                        } else if (championship.getType() == 2) {
                            if (teams.get(i).getTeam_uuid().equals(teams.get(i).getTeam1_uuid())) {
                                data.append("," + teams.get(i).getPoints1());
                            } else {
                                data.append("," + teams.get(i ).getPoints2());
                            }
                        }
                    }
                }
            }else {
                data.append(",No data avaliable");
            }


        } else {
            data.append("No Rounds have been played yet");
        }
        return data;
        }
}
