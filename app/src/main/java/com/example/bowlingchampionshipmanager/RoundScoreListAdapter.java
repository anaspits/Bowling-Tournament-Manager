package com.example.bowlingchampionshipmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoundScoreListAdapter extends RecyclerView.Adapter<RoundScoreListAdapter.BowlingViewHolder>  {


    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Participant> mNotes;

    public RoundScoreListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public RoundScoreListAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        RoundScoreListAdapter.BowlingViewHolder viewHolder = new RoundScoreListAdapter.BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoundScoreListAdapter.BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            Participant note = mNotes.get(position);
            holder.setData(String.valueOf(note.getFullName()),note.getHdcp(), position);
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

    public void setBowls(List<Participant> notes) {
        mNotes = notes;
        notifyDataSetChanged();

    }

    public void returnParticiapants(List<Participant> notes) {
        mNotes = notes;
        //((Activity)mContext).class.test();
        RoundActivity.getParticipantsofTeam(mNotes); //todo
    }


    public class BowlingViewHolder extends RecyclerView.ViewHolder {

        private TextView noteItemView;
        private EditText hdcp, first,second,thierd;
        private int mPosition;

        public BowlingViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            hdcp 	 = itemView.findViewById(R.id.txvHDCP);
            first 	 = itemView.findViewById(R.id.txv1);
            second 	 = itemView.findViewById(R.id.txv2);
            thierd = itemView.findViewById(R.id.txv3);
        }

        public void setData(String note, int phdcp, int position) {
            noteItemView.setText(note);
            hdcp.setText(phdcp);
            mPosition = position;
        }

    }

}
