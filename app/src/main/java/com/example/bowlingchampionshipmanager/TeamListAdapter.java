package com.example.bowlingchampionshipmanager;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.BowlingViewHolder>  {

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Team myNote);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Team> mNotes;
    private TeamListAdapter.OnDeleteClickListener onDeleteClickListener;

    public TeamListAdapter(Context context, TeamListAdapter.OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public TeamListAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        TeamListAdapter.BowlingViewHolder viewHolder = new TeamListAdapter.BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamListAdapter.BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            Team note = mNotes.get(position);
            holder.setData(note.getTeamName(),note.getSys_teamID(), position);
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

    public void setTeams(List<Team> notes) {
        mNotes = notes;
        notifyDataSetChanged();

    }

    public class BowlingViewHolder extends RecyclerView.ViewHolder {

        private TextView noteItemView,teamItemView;
        private int mPosition;
        private Button btDelete, btEdit;

        public BowlingViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            btDelete 	 = itemView.findViewById(R.id.ivRowDelete);
            btEdit 	 = itemView.findViewById(R.id.ivRowEdit);
            teamItemView = itemView.findViewById(R.id.txvTeam);
        }

        public void setData(String note, int teamid, int position) {
            noteItemView.setText(note);
            teamItemView.setText(String.valueOf(teamid));
            mPosition = position;
        }

        public void setListeners() {
            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditTeamActivity.class);
                    intent.putExtra("bowlId", mNotes.get(mPosition).getSys_teamID());
                    intent.putExtra("b_object", mNotes.get(mPosition));
                    ((Activity)mContext).startActivityForResult(intent, Create2Activity.UPDATE_TEAM_ACTIVITY_REQUEST_CODE);
                    //((Activity)mContext).startActivityForResult(intent, Create2Activity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
                }
            });
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.OnDeleteClickListener(mNotes.get(mPosition));
                    }
                }
            });
        }
    }

}
