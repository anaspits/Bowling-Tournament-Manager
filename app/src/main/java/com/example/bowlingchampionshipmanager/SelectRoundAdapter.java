package com.example.bowlingchampionshipmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectRoundAdapter extends RecyclerView.Adapter<SelectRoundAdapter.BowlingViewHolder>   {
    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Round> mNotes;
    private Championship ch;
    private Team team;
    private int position;
    private BowlingViewModel bowlingViewModel;
    private int finishedTeamflag;
    private String flag="none";

    public SelectRoundAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        bowlingViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(BowlingViewModel.class);
        finishedTeamflag=0;
        flag="none";
    }
    @NonNull
    @Override
    public SelectRoundAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list2, parent, false);
        SelectRoundAdapter.BowlingViewHolder viewHolder = new SelectRoundAdapter.BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectRoundAdapter.BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            Round note = mNotes.get(position);
            this.position=position;
            System.out.println("selroundadapter flag "+ finishedTeamflag);
            if(flag.equals("stat")){ //statistika
              /*  if (ch.getType() == 2) {
                    holder.setData(String.valueOf(note.getFroundid()),"TEAM:"+note.getTeam1ID()+" VS TEAM:"+note.getTeam2ID(), position);
                } else if (ch.getType() == 1) {
                    holder.setData(String.valueOf(note.getFroundid()),"TEAM:" + note.getTeam1ID(), position);
                }*/

                holder.setData(String.valueOf(note.getFroundid()),"", "",position);
            }else {
                if (finishedTeamflag == 1) { //finish team results
                    if (ch.getType() == 2) {
                        if (team.getUuid().equals(note.getTeam1UUID())) {
                            holder.setData(note.getFroundid() + ") VS Team:" + note.getTeam2ID(), String.valueOf(note.getPoints1()),String.valueOf(note.getScore1()), position);
                        } else if (team.getUuid().equals(note.getTeam2UUID())) {
                            holder.setData(note.getFroundid() + ") VS Team:" + note.getTeam1ID(), String.valueOf(note.getPoints2()),String.valueOf(note.getScore2()), position);
                        }
                    } else if (ch.getType() == 1 || ch.getType() == 4) {
                        holder.setData( String.valueOf(note.getFroundid()), String.valueOf(note.getPoints1()),String.valueOf(note.getScore1()), position);
                    }

                } else if (ch.getType() == 2) { //round
                    holder.setData("TEAM:" + note.getTeam1ID(), " VS TEAM:" + note.getTeam2ID(),"", position);
                } else if (ch.getType() == 1 || ch.getType() == 4) {
                    holder.setData("TEAM:" + note.getTeam1ID(), "","", position);
                }
            }
            holder.setListeners();
        } else {
            // Covers the case of data not being ready yet.
            holder.noteItemView.setText(R.string.exit);
        }
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    public void setSelRound(List<Round> notes) {
        mNotes = notes;
        notifyDataSetChanged();

    }

    public void setflag(String f) {
       flag=f;

    }

    public void setChamp(Championship champ) {
        ch = champ;
        System.out.println("selroundadapter champid = " + ch.getFchampID() + " " + ch.getUuid()+" "+ch.getStatus());
    }

    public void setSelTeam(Team team1) {
        team = team1;
    }
    public void setFinishedFlag(int i) {//gia tin omada pou teleiwse
        finishedTeamflag =i;
    }


    public class BowlingViewHolder extends RecyclerView.ViewHolder {

        private TextView noteItemView,teamItemView,txtscore;
        private int mPosition;
        private Button btSel;

        public BowlingViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            btSel 	 = itemView.findViewById(R.id.ivRowSelect);
            if( finishedTeamflag ==1 && flag.equals("none")){
                btSel.setVisibility(View.GONE);
                txtscore=itemView.findViewById(R.id.txtscore);
            }
            teamItemView = itemView.findViewById(R.id.txvTeam);
        }

        public void setData(String note, String second,String third, int position) {
            noteItemView.setText(note);
            teamItemView.setText(second);
            mPosition = position;
            if( finishedTeamflag ==1){
                txtscore.setText(third);
                //if(team.getUuid().equals(mNotes.get(getAdapterPosition()).getTeam1UUID())) {
                   // txtscore.setText(String.valueOf(mNotes.get(getAdapterPosition()).getScore1()));
                //} else if (team.getUuid().equals(mNotes.get(getAdapterPosition()).getTeam2UUID())) {
                   // txtscore.setText(String.valueOf(mNotes.get(getAdapterPosition()).getScore2()));

               // }
            }
        }
        public void setListeners() {
            btSel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag.equals("stat")) {
                        Intent intent = new Intent(mContext, RoundStatisticsActivity.class);
                        intent.putExtra("round", mNotes.get(mPosition));
                        intent.putExtra("roundsysid", mNotes.get(mPosition).getRoundid());
                        intent.putExtra("champ", ch);
                        mContext.startActivity(intent);
                    } else {
                        if (ch.getType() == 2) { //todo if flag stat
                        Intent intent = new Intent(mContext, RoundEditScoreActivity.class);
                        intent.putExtra("round", mNotes.get(mPosition));
                        intent.putExtra("roundsysid", mNotes.get(mPosition).getRoundid());
                        intent.putExtra("selTeam", team);
                        intent.putExtra("champ", ch);
                        //mContext.startActivity(intent);
                        ((Activity) mContext).startActivityForResult(intent, RoundActivity.UPDATE_SCORE_REQUEST_CODE);
                    }
                    //version 2
                    else if (ch.getType() == 1|| ch.getType() == 4) {
                        Intent intent = new Intent(mContext, PinsRoundEditActivity.class);
                        intent.putExtra("round", mNotes.get(mPosition));
                        intent.putExtra("roundsysid", mNotes.get(mPosition).getRoundid());
                        intent.putExtra("selTeam", team);
                        intent.putExtra("champ", ch);
                        ((Activity) mContext).startActivityForResult(intent, PinsRoundActivity.UPDATE_SCORE_REQUEST_CODE);
                    } //2
                }
                }
            });
        }
    }
}