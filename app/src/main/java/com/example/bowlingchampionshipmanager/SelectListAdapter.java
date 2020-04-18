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

import java.util.List;

public class SelectListAdapter  extends RecyclerView.Adapter<SelectListAdapter.BowlingViewHolder>  {

    private final LayoutInflater layoutInflater;
    private Context mContext;
   //fixme private List<Object> mNotes;
    private List<Team> mNotes;
    private Championship ch;
    private BowlingViewModel bowlingViewModel;

    public SelectListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        bowlingViewModel = ViewModelProviders.of((FragmentActivity) mContext).get(BowlingViewModel.class);


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
                Intent intent= new Intent(mContext,RoundActivity.class);
                @Override
                public void onClick(View v) {
                    bowlingViewModel.getNextRoundofTeamofChamp( mNotes.get(mPosition).getUuid(),ch.getUuid()).observe((LifecycleOwner) mContext, new Observer<List<Round>>() {
                        @Override
                        public void onChanged(List<Round> ro) {
                            if(ro.size()!=0) {
                                Round r = ro.get(0);
                                System.out.println("Sel Current Round of team "+mNotes.get(mPosition).getFTeamID()+" stat " + r.getStatus()+" is round "+r.getFroundid()+" with t1: "+r.getTeam1ID()+" and t2: "+ r.getTeam2ID()+" and sysID: "+r.getRoundid());
                                intent.putExtra("round", r);
                                /*if (tuuid.equals(r.getTeam1UUID())) {
                                    textTitle.append("\n"+"Team "+t.getTeamName()+" VS Team " + r.getTeam2ID());
                                } else {
                                    textTitle.append("\n"+"Team "+t.getTeamName()+" VS Team " + r.getTeam1ID());
                                }*/
                            }
                        }
                    });
                   // Intent intent= new Intent(mContext,RoundActivity.class);
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