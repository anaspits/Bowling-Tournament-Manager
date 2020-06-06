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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TeamandScoreAdapter  extends RecyclerView.Adapter<com.example.bowlingchampionshipmanager.TeamandScoreAdapter.BowlingViewHolder>  {

        private final LayoutInflater layoutInflater;
        private Context mContext;
        private List<TeamandScore> mNotes;
        private Championship ch;
        private int position;
        private static  Round round;
        private BowlingViewModel bowlingViewModel;
        private int finishedTeamflag;

        public static List<Round> rounds = new ArrayList<>(); //test

        public TeamandScoreAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
            mContext = context;
            bowlingViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(BowlingViewModel.class);
            finishedTeamflag=0;
        }

        @NonNull
        @Override
        public com.example.bowlingchampionshipmanager.TeamandScoreAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.list2, parent, false);
            com.example.bowlingchampionshipmanager.TeamandScoreAdapter.BowlingViewHolder viewHolder = new com.example.bowlingchampionshipmanager.TeamandScoreAdapter.BowlingViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.bowlingchampionshipmanager.TeamandScoreAdapter.BowlingViewHolder holder, int position) {

            if (mNotes != null) {
                TeamandScore note = mNotes.get(position);
                this.position=position;
                holder.setData(note.getTeam_name(),note.getTeam_score(), position);
                //holder.setListeners();
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

        public void setTeams(List<TeamandScore> notes) {
            mNotes = notes;
            notifyDataSetChanged();

        }

        public void setFinishedFlag(int i) {//gia tin omada pou teleiwse
            finishedTeamflag =i; //axristo
        }

        public void setChamp(Championship champ) {
            ch=champ;
            System.out.println("champid = "+ ch.getFchampID()+" "+ch.getUuid());
            }

        public class BowlingViewHolder extends RecyclerView.ViewHolder {

            private TextView noteItemView,teamItemView,txtscore;
            private int mPosition;
            private Button btSel;

            public BowlingViewHolder(@NonNull View itemView) {
                super(itemView);
                noteItemView = itemView.findViewById(R.id.txvNote);
                btSel 	 = itemView.findViewById(R.id.ivRowSelect);
                teamItemView = itemView.findViewById(R.id.txvTeam);
                txtscore=itemView.findViewById(R.id.txtscore);
            }

            public void setData(String note, int score, int position) {
                noteItemView.setText(note);
                txtscore.setText(String.valueOf(score));
                teamItemView.setText("");
                mPosition = position;
                btSel.setVisibility(View.GONE);
            }

            /*public void setListeners() { //todo na anigei activity me ta stoixeia twn omadwn opws editTeam H' finished
                btSel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            Intent intent = new Intent(mContext, FinishTeamActivity.class);
                            intent.putExtra("bowlId", mNotes.get(mPosition).getTeam_uuid());
                            intent.putExtra("count", getItemCount());
                            intent.putExtra("b_object", mNotes.get(mPosition));
                            intent.putExtra("champ", ch);
                            mContext.startActivity(intent);
                    }
                });
            }*/
}
    }
