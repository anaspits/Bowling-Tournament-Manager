package com.example.bowlingchampionshipmanager;

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

    public SelectRoundAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        bowlingViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(BowlingViewModel.class);
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
            holder.setData("t1:"+note.getTeam1ID()+" VS ","t2:"+note.getTeam2ID(), position);
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

    public void setChamp(Championship champ) {
        ch = champ;
        System.out.println("champid = " + ch.getFchampID() + " " + ch.getUuid());
    }

    public void setSelTeam(Team team1) {
        team = team1;

    }


    public class BowlingViewHolder extends RecyclerView.ViewHolder {

        private TextView noteItemView,teamItemView;
        private int mPosition;
        private Button btSel;

        public BowlingViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            btSel 	 = itemView.findViewById(R.id.ivRowSelect);
            teamItemView = itemView.findViewById(R.id.txvTeam);
        }

        public void setData(String note, String teamid, int position) {
            noteItemView.setText(note);
            teamItemView.setText(teamid);
            mPosition = position;
        }
        public void setListeners() {
            btSel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(mContext,RoundEditScoreActivity.class);
                    intent.putExtra("round", mNotes.get(mPosition));
                    intent.putExtra("roundsysid", mNotes.get(mPosition).getRoundid());
                    intent.putExtra("selTeam",team);
                    intent.putExtra("champ", ch);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}