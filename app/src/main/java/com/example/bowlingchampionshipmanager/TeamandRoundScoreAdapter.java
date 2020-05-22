package com.example.bowlingchampionshipmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TeamandRoundScoreAdapter extends RecyclerView.Adapter<com.example.bowlingchampionshipmanager.TeamandRoundScoreAdapter.BowlingViewHolder>  {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<TeamandRoundScore> mNotes;
    private Championship ch;
    private int position;


    public TeamandRoundScoreAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public com.example.bowlingchampionshipmanager.TeamandRoundScoreAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list2, parent, false);
        com.example.bowlingchampionshipmanager.TeamandRoundScoreAdapter.BowlingViewHolder viewHolder = new com.example.bowlingchampionshipmanager.TeamandRoundScoreAdapter.BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.bowlingchampionshipmanager.TeamandRoundScoreAdapter.BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            TeamandRoundScore note = mNotes.get(position);
            this.position=position;
            if (ch.getType()==1 || ch.getType() == 4) {
                holder.setData(note.getTeam_name(), note.getScore1(),note.getPoints1(), position);
            } else if(ch.getType()==2){
                if(note.getTeam_uuid().equals(note.getTeam1_uuid())){
                    holder.setData(note.getTeam_name(), note.getScore1(),note.getPoints1(), position);
                }else if(note.getTeam_uuid().equals(note.getTeam2_uuid())){
                    holder.setData(note.getTeam_name(), note.getScore2(), note.getPoints2(),position);
                }
            }
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

    public void setTeams(List<TeamandRoundScore> notes) {
        mNotes = notes;
        notifyDataSetChanged();

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

        public void setData(String note, int score, int points,int position) {
            noteItemView.setText(note);
            txtscore.setText(String.valueOf(score));
            teamItemView.setText(String.valueOf(points));
            mPosition = position;
            btSel.setVisibility(View.GONE);
        }

            /*public void setListeners() {
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
