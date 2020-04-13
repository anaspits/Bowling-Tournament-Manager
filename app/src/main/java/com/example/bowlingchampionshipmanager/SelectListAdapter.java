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

public class SelectListAdapter  extends RecyclerView.Adapter<SelectListAdapter.BowlingViewHolder>  {

    private final LayoutInflater layoutInflater;
    private Context mContext;
   //fixme private List<Object> mNotes;
    private List<Team> mNotes;
    private Championship ch;

    public SelectListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;

    }

    @NonNull
    @Override
    public SelectListAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list2, parent, false);
        SelectListAdapter.BowlingViewHolder viewHolder = new SelectListAdapter.BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectListAdapter.BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            Team note = mNotes.get(position);
            holder.setData(note.getTeamName(),note.getFTeamID(), position);
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

    public void setSelected(List<Team> notes) {
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
        private Button btSel;

        public BowlingViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            btSel 	 = itemView.findViewById(R.id.ivRowSelect);
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
                    Intent intent= new Intent(mContext,RoundActivity.class);
                    intent.putExtra("bowlId", mNotes.get(mPosition).getSys_teamID());
                    intent.putExtra("count", getItemCount());
                    intent.putExtra("b_object", mNotes.get(mPosition));
                    intent.putExtra("champ", ch);
                    //((Activity)mContext).startActivityForResult(intent,SelectTeamActivity.SELECT_TEAM_ACTIVITY_REQUEST_CODE);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}