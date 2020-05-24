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

public class ChampListAdapter extends RecyclerView.Adapter<ChampListAdapter.BowlingViewHolder>  {

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Championship myNote);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Championship> mNotes;
    private OnDeleteClickListener onDeleteClickListener;
    public String flag_stat="none";

    public ChampListAdapter(Context context, OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
        flag_stat="none";
    }

    @NonNull
    @Override
    public BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        BowlingViewHolder viewHolder = new BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            Championship note = mNotes.get(position);
            //HDCPActivity.champ= note;
            holder.setData(note.getStatus(),note.getSys_champID(), position);
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

    public void setChamp(List<Championship> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    public void setFlagStat(String f) {
        flag_stat = f;

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

        public void setData(String note, int chid, int position) {
            noteItemView.setText(String.valueOf(chid));
            teamItemView.setText(note);
            mPosition = position;
        }

        public void setListeners() {
            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditChampActivity.class);
                    intent.putExtra("bowlId", mNotes.get(mPosition).getSys_champID());
                    intent.putExtra("b_object", mNotes.get(mPosition));
                    ((Activity)mContext).startActivityForResult(intent, ContinueChampActivity.UPDATE_CHAMP_ACTIVITY_REQUEST_CODE);
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
            btSel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (flag_stat.equals("champ_stat")) {
                        Intent intent = new Intent(mContext, FinishChampActivity.class);
                        intent.putExtra("champuuid", mNotes.get(mPosition).getUuid());
                        intent.putExtra("champ", mNotes.get(mPosition));
                        mContext.startActivity(intent);
                        ((Activity)mContext).finish();
                    } else if (flag_stat.equals("rounds_stat")) {
                        Intent intent = new Intent(mContext, SelectStatforRoundActivity.class);
                        intent.putExtra("flag", "stat");
                        intent.putExtra("champ", mNotes.get(mPosition));
                        mContext.startActivity(intent);
                        ((Activity)mContext).finish();
                    } else if (flag_stat.equals("teams_stat")) {
                        Intent intent = new Intent(mContext, SelectTeamActivity.class);
                        intent.putExtra("flag", "stat");
                        intent.putExtra("champ", mNotes.get(mPosition));
                        mContext.startActivity(intent);
                        ((Activity)mContext).finish();
                    }/*else if (flag_stat.equals("players_stat")) {
                        Intent intent = new Intent(mContext, SelectStatforPlayersActivity.class);
                        intent.putExtra("flag", "stat");
                        intent.putExtra("champ", mNotes.get(mPosition));
                        mContext.startActivity(intent);
                    }*/else {
                        if (mNotes.get(mPosition).getStatus().equals("Finished")) {
                            Intent intent = new Intent(mContext, FinishChampActivity.class);
                            intent.putExtra("champuuid", mNotes.get(mPosition).getUuid());
                            intent.putExtra("champ", mNotes.get(mPosition));
                            mContext.startActivity(intent);
                            ((Activity)mContext).finish();
                        } else if(mNotes.get(mPosition).getStatus().equals("started")){
                            Intent intent = new Intent(mContext, SelectTeamActivity.class);
                            intent.putExtra("champuuid", mNotes.get(mPosition).getUuid());
                            intent.putExtra("champ", mNotes.get(mPosition));
                            intent.putExtra("flag", "start");
                            //((Activity)mContext).startActivityForResult(intent,SelectTeamActivity.SELECT_TEAM_ACTIVITY_REQUEST_CODE);
                            mContext.startActivity(intent);
                            ((Activity)mContext).finish();
                        }else if(mNotes.get(mPosition).getStatus().equals("created")){
                                Intent intent = new Intent(mContext, Create2Activity.class); //afou sto pins kanw insert otan pati8ei to start champ
                                intent.putExtra("champuuid", mNotes.get(mPosition).getUuid());
                                intent.putExtra("champ", mNotes.get(mPosition));
                                intent.putExtra("flag", "start");
                                mContext.startActivity(intent);
                                ((Activity) mContext).finish();

                        }
                    }
                }
            });
        }
    }
}