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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//axristo
public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.BowlingViewHolder>  { //malon axristo

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Team_detail myNote);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Team_detail> mNotes;
    private DetailListAdapter.OnDeleteClickListener onDeleteClickListener;

    public DetailListAdapter(Context context, DetailListAdapter.OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public DetailListAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        DetailListAdapter.BowlingViewHolder viewHolder = new DetailListAdapter.BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailListAdapter.BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            Team_detail note = mNotes.get(position);
            holder.setData(String.valueOf(note.getTeamID()),note.getParticipantID(), position);
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

    public void setTeam_detail(List<Team_detail> notes) {
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

        public void setData(String note, String teamid, int position) {
            noteItemView.setText("teamid "+note);
            teamItemView.setText("participantid " +String.valueOf(teamid));
            mPosition = position;
        }

        public void setListeners() {
           /* btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditTeamActivity.class);
                    intent.putExtra("bowlId", mNotes.get(mPosition).getSys_teamDetailID());
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
            });*/
        }
    }

}
