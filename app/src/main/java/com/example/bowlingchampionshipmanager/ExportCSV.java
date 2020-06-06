package com.example.bowlingchampionshipmanager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ExportCSV {

    public ExportCSV() {
    }

    public StringBuilder exportRoundEditScore(Championship championship, Round r, Team team1, Team team2, List<Participant> players1, List<Participant> players2, List<Round_detail> rd1, List<Round_detail> rd2, int first_sum1, int second_sum1, int third_sum1, int sum_hdcp1, int first_sum2, int second_sum2, int third_sum2, int sum_hdcp2) {
        StringBuilder data = new StringBuilder();

        data.append("Championship No.," + championship.getSys_champID() + ",UUID:," + championship.getUuid() + "\n");
        data.append("\nRound No.," + r.getFroundid() + ",Date," + Calendar.getInstance().getTime()+"\n");
        data.append("\nTeam," + team1.getTeamName() + ",Lane,"+r.getLanes());
        data.append("\n,Player,HDCP,1,2,3,Sum");
        exportRoundEditScoreforTeam(team1, players1, rd1, data, first_sum1, second_sum1, third_sum1, sum_hdcp1, r);
        if (championship.getType() == 2) { //tvt
            data.append("\nVS");
            data.append("\nTeam," + team2.getTeamName() + ",Lane,"+r.getLanes());
            data.append("\n,Player,HDCP,1,2,3,Sum");
            exportRoundEditScoreforTeam(team2, players2, rd2, data, first_sum2, second_sum2, third_sum2, sum_hdcp2, r);
        }
        return data;
    }

    public void exportRoundEditScoreforTeam(Team team, List<Participant> players, List<Round_detail> rd, StringBuilder data, int first_sum, int second_sum, int third_sum, int sum_hdcp, Round r) {
        for (int i = 0; i < players.size(); i++) {
            if(rd.get(i).getBlind()==0) {
                data.append("\n" + (i + 1) + "," + players.get(i).getFullName() + "," + rd.get(i).getHdcp() + "," + rd.get(i).getFirst() + "," + rd.get(i).getSecond() + "," + rd.get(i).getThird() + "," + (rd.get(i).getFirst() + rd.get(i).getSecond() + rd.get(i).getThird()));
            }else {
                data.append("\n" + (i + 1) + ",BLIND," + 0 + "," + rd.get(i).getFirst() + "," + rd.get(i).getSecond() + "," + rd.get(i).getThird() + "," + (rd.get(i).getFirst() + rd.get(i).getSecond() + rd.get(i).getThird()));

            }
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
        data.append("\nRound,,Points,Score,Lanes");
        for (int i = 0; i < rounds.size(); i++) {
            data.append("\n" + rounds.get(i).getFroundid() + "," + rounds.get(i).getTeam1ID() + "," + rounds.get(i).getPoints1() + "," + rounds.get(i).getScore1()+","+rounds.get(i).getLanes());
            if (championship.getType() == 2) {
                data.append("\n" + ",VS " + rounds.get(i).getTeam2ID() + "," + rounds.get(i).getPoints2() + "," + rounds.get(i).getScore2()+","+rounds.get(i).getLanes());
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
        int counter=1;
        for (int i = 0; i < players.size(); i++) {
            if (!players.get(i).getParticipant_uuid().equals("blind")) {
                data.append("\n" + counter+ "," + players.get(i).getFirstName() + players.get(i).getLastName() + "," + players.get(i).getBowlAvg() + "," + players.get(i).getHdcp() + "," + players.get(i).getGames());
                counter++;
            }
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
        data.append("\nRound,Team,Points,Score,Lanes");
        for (int i = 0; i < rounds.size(); i++) {
            data.append("\n" + rounds.get(i).getFroundid() + "," + rounds.get(i).getTeam1ID() + "," + rounds.get(i).getPoints1() + "," + rounds.get(i).getScore1()+","+rounds.get(i).getLanes());
            if (championship.getType() == 2) {
                data.append("\n" + ",VS " + rounds.get(i).getTeam2ID() + "," + rounds.get(i).getPoints2() + "," + rounds.get(i).getScore2()+","+rounds.get(i).getLanes());
            }
        }

        if(championship.getStatus().equals("Finished")) {
            data.append("\n");
        data.append("\nPlayers");
            data.append("\n,Player,Average,HDCP,Games");
            int counter=1;
            for (int i = 0; i < players.size(); i++) {
                if (!players.get(i).getParticipant_uuid().equals("blind")) {
                    data.append("\n" + counter + "," + players.get(i).getFirstName() + players.get(i).getLastName() + "," + players.get(i).getBowlAvg() + "," + players.get(i).getHdcp() + "," + players.get(i).getGames());
                counter++;
                }
            }
        }
        return data;
    }


    public StringBuilder exportRoundTeamStat(Championship championship, List<Round> rounds,List<TeamandRoundScore> teams, List<TeammatesTuple> playersandteams, List<Championship_detail> cds)  {
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
            System.out.println("teams size "+teams.size());
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
                        if (teams.get(i).getStatus().equals("ignore")){
                            data.append("," + "Bye");
                        }else if (championship.getType() == 1 || championship.getType() == 4) { //pairnw ta points tou prwtou gyrou
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
                                for (int c = 0; c < cds.size(); c++) {
                                    System.out.println("proteleftaio "+ cds.get(c).getScore());
                                    if (teams.get(i - 1).getTeam_uuid().equals(cds.get(c).getSys_teamID())){
                                        data.append("," + cds.get(c).getScore());
                                        break;
                                    }
                                }

                          /*  if (championship.getType() == 1 || championship.getType() == 4) { //tote kanw oti kanw sthn else, dld pairnw ta score ths prohgoumenhs omadas
                                data.append("," + teams.get(i - 1).getScore1());
                              } else if (championship.getType() == 2) {
                                if (teams.get(i - 1).getTeam_uuid().equals(teams.get(i - 1).getTeam1_uuid())) {
                                    data.append("," + teams.get(i - 1).getScore1());
                                } else {
                                    data.append("," + teams.get(i - 1).getScore2());
                                }
                            } */
                            data.append("\n" + counter + "," + String.valueOf(teams.get(i).getTeam_name())+")"); //kai meta pairnw to onoma k tous paiktes afths ths omadas
                            for (int j = 0; j < playersandteams.get((counter-1)).getT().size(); j++) {
                                data.append(playersandteams.get((counter-1)).getT().get(j).getLastName());
                                if(j!=(playersandteams.get((counter-1)).getT().size()-1)){
                                    data.append("-");
                                }
                            }
                        }
                            //epishs pairnw ta points afths ths omadas
                        if (teams.get(i).getStatus().equals("ignore")){ //an den epai3e afton ton gyro vazw bye alliws..
                            data.append("," + "Bye");
                        }else if (championship.getType() == 1 || championship.getType() == 4) {
                            data.append("," + teams.get(i).getPoints1());
                        } else if (championship.getType() == 2) {
                            if (teams.get(i).getTeam_uuid().equals(teams.get(i).getTeam1_uuid())) {
                                data.append("," + teams.get(i).getPoints1());
                            } else {
                                data.append("," + teams.get(i).getPoints2());
                            }
                        }
                        //kai to score ths (afou einai 1 gyros mono ara einai kai o teleftaios ths)
                            for (int c = 0; c < cds.size(); c++) {
                                if (teams.get(i).getTeam_uuid().equals(cds.get(c).getSys_teamID())){
                                    System.out.println("teleftaio "+cds.get(c).getScore());
                                    data.append("," + cds.get(c).getScore());
                                    break;
                                }
                            }

                      /*  if (championship.getType() == 1 || championship.getType() == 4) {
                            data.append("," + t.getScore1());
                        } else if (championship.getType() == 2) {
                            if (t.getTeam_uuid().equals(t.getTeam1_uuid())) {
                                data.append("," + t.getScore1());
                            } else {
                                data.append("," + t.getScore2());
                            }
                        } */
                    } else if ( t.getTeam_uuid().equals(teams.get(i - 1).getTeam_uuid())) { //an einai idia omada me thn prohgoumenh, shmainei oti phgame ston epomeno gyro (ths idias omadas)
                        System.out.println(" idia me prohg + status "+t.getStatus()+ " t "+t.getTeam_name()+" r ");
                        if (t.getStatus().equals("ignore")){
                            data.append("," + "Bye");
                        }else if (championship.getType() == 1 || championship.getType() == 4) { //ara pairnoume mono ta points ths gia afton ton gyro
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
                        System.out.println(" else + status "+teams.get(i - 1).getStatus()+ " t "+teams.get(i - 1).getTeam_name()+" r ");
                            for (int c = 0; c < cds.size(); c++) {
                                if (teams.get(i - 1).getTeam_uuid().equals(cds.get(c).getSys_teamID())){
                                    System.out.println("else prohg "+cds.get(c).getScore());
                                    data.append("," + cds.get(c).getScore());
                                    break;
                                }
                            }

                        /*if (championship.getType() == 1 || championship.getType() == 4) { //opote prepei na paroume to score ths prohgoumenhs
                            data.append("," + teams.get(i - 1).getScore1());
                        } else if (championship.getType() == 2) {
                            if (teams.get(i - 1).getTeam_uuid().equals(teams.get(i - 1).getTeam1_uuid())) {
                                data.append("," + teams.get(i - 1).getScore1());
                            } else {
                                data.append("," + teams.get(i - 1).getScore2());
                            }
                        } */
                        data.append("\n" + counter + "," + String.valueOf(teams.get(i).getTeam_name())+")"); //kai na perasoume se afthn pou eimaste twra (dld thn epomenh) kai na paroume to onoma kai tous paiktes ths
                        for (int j = 0; j < playersandteams.get((counter-1)).getT().size(); j++) {
                            data.append(playersandteams.get((counter-1)).getT().get(j).getLastName());
                            if(j!=(playersandteams.get((counter-1)).getT().size()-1)){
                                data.append("-");
                            }
                        }
                        counter++;
                        System.out.println(" else afth + status "+teams.get(i - 1).getStatus()+ " t "+teams.get(i - 1).getTeam_name()+" r ");
                        if (teams.get(i).getStatus().equals("ignore")){
                            data.append("," + "Bye");
                        }else if (championship.getType() == 1 || championship.getType() == 4) { //kai ta points ths (gia ton prwto ths gyro)
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


    public StringBuilder exportRoundPlayersStat(Championship championship, List<Round> rounds,List<PlayerandGames> rd) {
        StringBuilder data = new StringBuilder();
        data.append("Championship No.," + championship.getSys_champID() + ",UUID:," + championship.getUuid()+",Start Date:,"+championship.getStart_date()+"\n");
        data.append(" ,Name,");

        for (int i = 0; i < rounds.size(); i++) {
            if(i==0) {
                data.append(rounds.get(i).getFroundid()+"H,");
                data.append(rounds.get(i).getFroundid()+"H,");
                data.append(rounds.get(i).getFroundid()+"H,");
            } else if (rounds.get(i).getFroundid()!=rounds.get(i-1).getFroundid()){
                data.append(rounds.get(i).getFroundid()+"H,");
                data.append(rounds.get(i).getFroundid()+"H,");
                data.append(rounds.get(i).getFroundid()+"H,");
            } else if (rounds.get(i).getFroundid()==rounds.get(i-1).getFroundid()){
                System.out.println("i "+i+" fr "+rounds.get(i).getFroundid());
                //break() H continue();
            }
        }
        data.append("Avg,Games,Score,HDCP");

        int counter=1;
        for (int i = 0; i < rd.size(); i++) { // paiktes kai ta rd tous me order by participantsuuid
            PlayerandGames p = rd.get(i);
            if (!p.getParticipant_uuid().equals("blind")) { //an einai o dummy paikths ton agnow
                if (i == 0) { //an einai to prwto, einai o prwtos paikths ston prwto tou gyro
                    data.append("\n" + counter + "," + String.valueOf(p.getFirstName()) + " " + p.getLastName()); //pairnw to onma tou
                    counter++;
                    if(p.getRound_updated_date()!=null) { //an exei updateddate ara den einai gyros bye, ara pairnw to score tou
                        data.append("," + String.valueOf(p.getFirst()) + "," + String.valueOf(p.getSecond()) + "," + String.valueOf(p.getThird()));
                    }else {
                        data.append(",-,-,-"); //an den exei updateddate ara einai gyros bye
                    }
                } else if (p.getParticipant_uuid().equals(rd.get(i - 1).getParticipant_uuid())) { //an to uuid einai idio me to prohgoumeno ara einai o idios aikths se allo gyro
                    if(p.getRound_updated_date()!=null) {
                        data.append("," + String.valueOf(p.getFirst()) + "," + String.valueOf(p.getSecond()) + "," + String.valueOf(p.getThird()));
                    }else {
                        data.append(",-,-,-");
                    }
                    if (i == (rd.size() - 1)) { //gia to teleftaio stoixeio pou 8a einai o teleftaios gyros tou teleftaiou paikth
                        if(p.getParticipant_uuid().equals(rd.get(i-1).getParticipant_uuid()) && p.getRound_updated_date()==null) { //an o paikths einai idios me ton prohgoume (ara exei prohgoumeno gyro kai den einai paixnidi enos gyrou) kai o teleftaios aftos gyros htan bye
                            data.append("," + rd.get(i-1).getBowlAvg() + "," + rd.get(i-1).getGames() + "," + (rd.get(i-1).getBowlAvg() * rd.get(i-1).getGames())+","+rd.get(i-1).getHdcp()); //tote 8a parw ta score apo ton prohgoumeno gyro pou den htan bye kai ara htan o teleftaios gyros pou epai3e o paikths
                        }else { //alliws ta pairnw kanonika
                            data.append("," + rd.get(i).getBowlAvg() + "," + rd.get(i).getGames() + "," + (rd.get(i).getBowlAvg() * rd.get(i).getGames())+","+rd.get(i).getHdcp());
                        }
                    }
                } else { //an eimaste sthn allagh apo ton enan paikth ston epomeno (ara einai o teleftaios gyrous tou prohgoumenou kai o prwtos gyros aftou pou meletame , dld tou epomenou)
                    if(rd.get(i-1).getParticipant_uuid().equals(rd.get(i-2).getParticipant_uuid()) && rd.get(i-1).getRound_updated_date()==null){ //an o teleftaios gyrous tou prohgoumenou paikth htan bye kai eixe ki allon gyro prin ton prohgoumeno
                        data.append("," + rd.get(i - 2).getBowlAvg() + "," + rd.get(i - 2).getGames() + "," + (rd.get(i - 2).getBowlAvg() * rd.get(i - 2).getGames())+","+rd.get(i-2).getHdcp()); //fixme tou jhonnie den doulevei deixnei 0 //pairnoume ta score tou proprohgoumenou gyrou tou paikth aftou
                    }else {
                        data.append("," + rd.get(i - 1).getBowlAvg() + "," + rd.get(i - 1).getGames() + "," + (rd.get(i - 1).getBowlAvg() * rd.get(i - 1).getGames())+","+rd.get(i-1).getHdcp()); //alliws pairnoume ta score tou prohgoumenou
                    }

                    data.append("\n" + counter + "," + String.valueOf(p.getFirstName()) + " " + p.getLastName() ); //pairnoume to onoma tou paikth
                    counter++;
                    if(p.getRound_updated_date()!=null) {
                        data.append(","+String.valueOf(p.getFirst()) + "," + String.valueOf(p.getSecond()) + "," + String.valueOf(p.getThird())); //kai ta score tou prwtou tou gyrou
                    }else {
                        data.append(",-,-,-");
                    }
                }
            }
        }
        return data;
    }

    public StringBuilder exportSpecificRoundStat(Championship championship, Round r, int max, TeamandRoundScore winner, List<TeammatesTuple> playersandteams, List<TeamandRoundScore> teams, List<PlayerandGames> players, List<PlayerandGames> rd, ArrayList<Integer> draw_mangame, ArrayList<Integer> draw_mangamebegining, ArrayList<Integer> draw_manset, ArrayList<Integer> draw_mansetbegining, ArrayList<Integer> draw_fgame, ArrayList<Integer> draw_fgamebegining, ArrayList<Integer> draw_fset, ArrayList<Integer> draw_fsetbegining){
        StringBuilder data = new StringBuilder();
        data.append("Championship No.,"+championship.fchampID+",UUID:,"+championship.getUuid());
        data.append("\nRound No.,"+r.getFroundid()); //todo na valw kai lanes?
        data.append("\nWinning Team of Round,"+winner.getTeam_name()+",Points: "+max);
        data.append("\nTeam Ranking");
        data.append("\n,Team,Points,Score");
        for (int i=0;i<teams.size();i++){
            data.append("\n"+teams.get(i).getTeam_name()+",");
            for (int j = 0; j < playersandteams.get(i).getT().size(); j++) {
                data.append(playersandteams.get(i).getT().get(j).getLastName());
                if(j!=(playersandteams.get(i).getT().size()-1)){
                    data.append("-");
                }
            }
            if(teams.get(i).getTeam_uuid().equals(teams.get(i).getTeam1_uuid())) {
                data.append("," + teams.get(i).getPoints1()+","+teams.get(i).getScore1());
            }else {
                data.append("," + teams.get(i).getPoints2()+","+teams.get(i).getScore2());
            }
        }

        data.append("\nPlayers");
        data.append("\n,Player,Average,HDCP,Games");
        for (int i=0;i<players.size();i++){
            data.append("\n"+(i+1)+","+players.get(i).getFirstName()+players.get(i).getLastName()+","+players.get(i).getBowlAvg()+","+players.get(i).getHdcp()+","+players.get(i).getGames());
        }

        data.append("\n");
        //data.append("\n,Paixnidi Antrwn,,Ap' Arxhs");
        //data.append("\n,"+rd.get(pos2).getLastName()+" "+rd.get(pos2).getFirstName()+","+mangame+","+rd.get(pos).getLastName()+" "+rd.get(pos).getFirstName()+","+mangamebegining);
        data.append("\n,Paixnidi Antrwn");
        for(int i=1;i<draw_mangame.size();i++){ //epeidh to draw_mgame.get(0) exei to mangame
            data.append("\n,"+rd.get(draw_mangame.get(i)).getLastName()+" "+rd.get(draw_mangame.get(i)).getFirstName()+","+draw_mangame.get(0)); //draw_mgame.get(0)=mangame
        }
        data.append("\n");
        data.append("\n,Paixnidi Antrwn Ap' Arxhs");
        for(int i=1;i<draw_mangamebegining.size();i++){ //epeidh to draw_mgame.get(0) exei to mangame
            data.append("\n,"+rd.get(draw_mangamebegining.get(i)).getLastName()+" "+rd.get(draw_mangamebegining.get(i)).getFirstName()+","+draw_mangamebegining.get(0)); //draw_mgame.get(0)=mangame
        }
        data.append("\n");
        data.append("\n,Paixnidi Gynaikwn");
        //data.append("\n,"+rd.get(fpos2).getLastName()+" "+rd.get(fpos2).getFirstName()+","+fgame+","+rd.get(fpos).getLastName()+" "+rd.get(fpos).getFirstName()+","+fgamebegining);
        for(int i=1;i<draw_fgame.size();i++){ //epeidh to draw_mgame.get(0) exei to mangame
            data.append("\n,"+rd.get(draw_fgame.get(i)).getLastName()+" "+rd.get(draw_fgame.get(i)).getFirstName()+","+draw_fgame.get(0)); //draw_mgame.get(0)=mangame
        }
        data.append("\n");
        data.append("\n,Paixnidi Gynaikwn Ap' Arxhs");
        for(int i=1;i<draw_fgamebegining.size();i++){ //epeidh to draw_mgame.get(0) exei to mangame
            data.append("\n,"+rd.get(draw_fgamebegining.get(i)).getLastName()+" "+rd.get(draw_fgamebegining.get(i)).getFirstName()+","+draw_fgamebegining.get(0)); //draw_mgame.get(0)=mangame
        }
        data.append("\n");
        data.append("\n,Set Antrwn");
        //data.append("\n,"+rd.get(pos4).getLastName()+" "+rd.get(pos4).getFirstName()+","+manset+","+rd.get(pos3).getLastName()+" "+rd.get(pos3).getFirstName()+","+mansetbegining);
        for(int i=1;i<draw_manset.size();i++){ //epeidh to draw_mgame.get(0) exei to mangame
            data.append("\n,"+rd.get(draw_manset.get(i)).getLastName()+" "+rd.get(draw_manset.get(i)).getFirstName()+","+draw_manset.get(0)); //draw_mgame.get(0)=mangame
        }
        data.append("\n");
        data.append("\n,Set Antrwn Ap' Arxhs");
        for(int i=1;i<draw_mansetbegining.size();i++){ //epeidh to draw_mgame.get(0) exei to mangame
            data.append("\n,"+rd.get(draw_mansetbegining.get(i)).getLastName()+" "+rd.get(draw_mansetbegining.get(i)).getFirstName()+","+draw_mansetbegining.get(0)); //draw_mgame.get(0)=mangame
        }
        data.append("\n");
        data.append("\n,Set Gynaikwn");
        //data.append("\n,"+rd.get(pos4).getLastName()+" "+rd.get(pos4).getFirstName()+","+manset+","+rd.get(pos3).getLastName()+" "+rd.get(pos3).getFirstName()+","+mansetbegining);
        for(int i=1;i<draw_fset.size();i++){ //epeidh to draw_mgame.get(0) exei to mangame
            data.append("\n,"+rd.get(draw_fset.get(i)).getLastName()+" "+rd.get(draw_fset.get(i)).getFirstName()+","+draw_fset.get(0)); //draw_mgame.get(0)=mangame
        }
        data.append("\n");
        data.append("\n,Set Gynaikwn Ap' Arxhs");
        for(int i=1;i<draw_fsetbegining.size();i++){ //epeidh to draw_mgame.get(0) exei to mangame
            data.append("\n,"+rd.get(draw_fsetbegining.get(i)).getLastName()+" "+rd.get(draw_fsetbegining.get(i)).getFirstName()+","+draw_fsetbegining.get(0)); //draw_mgame.get(0)=mangame
        }
        //data.append("\n,"+rd.get(pos4).getLastName()+" "+rd.get(fpos4).getFirstName()+","+fset+","+rd.get(fpos3).getLastName()+" "+rd.get(fpos3).getFirstName()+","+fsetbegining);

        return data;
    }

}
