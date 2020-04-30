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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectParticipantListAdapter extends RecyclerView.Adapter<SelectParticipantListAdapter.BowlingViewHolder>  {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Participant> mNotes;
    private Championship ch;
    private Team team;
    private int position;
    private static  Round round;
    private BowlingViewModel bowlingViewModel;
    private int finishedflag;

    public SelectParticipantListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        bowlingViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(BowlingViewModel.class);
    }

    @NonNull
    @Override
    public SelectParticipantListAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list2, parent, false);
        SelectParticipantListAdapter.BowlingViewHolder viewHolder = new SelectParticipantListAdapter.BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectParticipantListAdapter.BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            Participant note = mNotes.get(position);
            this.position=position;
            holder.setData(note.getFullName(),note.getBowlAvg(), position);
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

    public void setSelected(List<Participant> notes) {
        mNotes = notes;
        notifyDataSetChanged();

    }

    public void setChamp(Championship champ) {
        ch=champ;
        System.out.println("champid = "+ ch.getFchampID()+" "+ch.getUuid());
        }

    public void setTeam(Team t) { //axristo
        team=t;
    }

    public void setFinishedFlag(int i) {//gia tin omada pou teleiwse
        finishedflag=i;
    }

    public class BowlingViewHolder extends RecyclerView.ViewHolder {

        private TextView noteItemView,teamItemView;
        private int mPosition;
        private Button btSel;

        public BowlingViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            btSel 	 = itemView.findViewById(R.id.ivRowSelect);
            if( finishedflag==1){
                btSel.setVisibility(View.GONE);
            }
            teamItemView = itemView.findViewById(R.id.txvTeam);
        }

        public void setData(String note, int teamid, int position) {
            noteItemView.setText(note);
            teamItemView.setText(String.valueOf(teamid));
            mPosition = position;
        }

        public void setListeners() {
            btSel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
/*
                        Intent intent = new Intent(mContext, RoundActivity.class);
                        intent.putExtra("bowlId", mNotes.get(mPosition).getParticipantID());
                        intent.putExtra("count", getItemCount());
                        intent.putExtra("b_object", mNotes.get(mPosition));
                        intent.putExtra("champ", ch);
                        //((Activity)mContext).startActivityForResult(intent,SelectTeamActivity.SELECT_TEAM_ACTIVITY_REQUEST_CODE);
                        mContext.startActivity(intent);
                    */
                }
            });
        }
    }
}