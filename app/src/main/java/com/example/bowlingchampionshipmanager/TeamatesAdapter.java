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

public class TeamatesAdapter extends RecyclerView.Adapter<TeamatesAdapter.BowlingViewHolder>  {

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(TeammatesTuple myNote);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<TeammatesTuple> mNotes;
    private Championship ch;
    private TeamatesAdapter.OnDeleteClickListener onDeleteClickListener;

    public TeamatesAdapter(Context context, TeamatesAdapter.OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public TeamatesAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        TeamatesAdapter.BowlingViewHolder viewHolder = new TeamatesAdapter.BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TeamatesAdapter.BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            TeammatesTuple note = mNotes.get(position);
            holder.setData(note.getC().getTeamName(),note.getT(), position);
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

    public void setTeams(List<TeammatesTuple> notes) {
        mNotes = notes;
        notifyDataSetChanged();

    }

    public void setChamp(Championship champ) {
        ch=champ;
        System.out.println("champid = "+ ch.getFchampID()+" "+ch.getUuid());
    }

    public class BowlingViewHolder extends RecyclerView.ViewHolder {

        private TextView noteItemView,teamItemView;
        private int mPosition;
        private Button btDelete, btEdit,btSel;

        public BowlingViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            btDelete 	 = itemView.findViewById(R.id.ivRowDelete);
            btEdit 	 = itemView.findViewById(R.id.ivRowEdit);
            btSel 	 = itemView.findViewById(R.id.ivRowSelect);
            teamItemView = itemView.findViewById(R.id.txvTeam);
        }

        public void setData(String note, List<Participant> part, int position) {
            noteItemView.setText(note);
            teamItemView.setText("");
            for(int i=0;i<part.size();i++) {
                teamItemView.append(String.valueOf(part.get(i).getFullName()));
            }
            mPosition = position;
        }

        public void setListeners() {
            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditTeamActivity.class);
                    intent.putExtra("bowlId", mNotes.get(mPosition).getC().getSys_teamID());
                    intent.putExtra("b_object", mNotes.get(mPosition).getC());
                    intent.putExtra("count", getItemCount());
                    intent.putExtra("champ", ch);
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
            btSel.setOnClickListener(new View.OnClickListener() { //na svisw
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(mContext,Create2Activity.class); //fixme
                    intent.putExtra("bowlId", mNotes.get(mPosition).getC().getSys_teamID());
                    intent.putExtra("count", getItemCount());
                    intent.putExtra("b_object", mNotes.get(mPosition).getC());
                    ((Activity)mContext).startActivityForResult(intent,Create1Activity.SELECT_TEAM_ACTIVITY_REQUEST_CODE);
                }
            });
        }
    }

}
