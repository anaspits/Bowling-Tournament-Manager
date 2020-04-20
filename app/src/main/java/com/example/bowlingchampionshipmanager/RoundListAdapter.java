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

public class RoundListAdapter extends RecyclerView.Adapter<RoundListAdapter.BowlingViewHolder>  {

    public interface OnDeleteClickListener { //axristo
        void OnDeleteClickListener(Round myNote);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    public static List<Round> mNotes;
    private RoundListAdapter.OnDeleteClickListener onDeleteClickListener;

    public RoundListAdapter(Context context, RoundListAdapter.OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public RoundListAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);
        RoundListAdapter.BowlingViewHolder viewHolder = new RoundListAdapter.BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoundListAdapter.BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            Round note = mNotes.get(position);
            holder.setData(String.valueOf(note.getFroundid()),"t1:"+note.getTeam1ID()+"t2:"+note.getTeam2ID(), position);
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

    public void setRounds(List<Round> notes) {
        mNotes = notes;
        notifyDataSetChanged();

    }

    public void returnRounds(List<Round> notes) { //axristo
        mNotes = notes;
        //((Activity)mContext).class.test();
        Teamsvsteams1Activity.test2(mNotes);
    }

    public void returnRounds2(List<Round> notes) {
        mNotes = notes;
        //((Activity)mContext).class.test();
        RoundActivity.getRoundsofTeam(mNotes);
    }
    public List<Round> returnRounds3(List<Round> notes) { //mallon axristo
        mNotes = notes;
        //((Activity)mContext).class.test();
        return (mNotes);
    }
    public void returnCurrentRound(Round r) {
        RoundActivity.getRoundofTeam(r);
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

        public void setData(String note, String teamid, int position) {
            noteItemView.setText(note);
            teamItemView.setText(teamid);
            mPosition = position;
        }

        public void setListeners() {
            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  Intent intent = new Intent(mContext, EditActivity.class);
                    intent.putExtra("bowlId", mNotes.get(mPosition).getRoundid());
                    intent.putExtra("b_object", mNotes.get(mPosition));
                    intent.putExtra("count", getItemCount());
                    ((Activity)mContext).startActivityForResult(intent, Create1Activity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE); */
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
                    Intent intent= new Intent(mContext,mContext.getClass()); //fixme
                    intent.putExtra("bowlId", mNotes.get(mPosition).getRoundid());
                    intent.putExtra("count", getItemCount());
                    intent.putExtra("b_object", mNotes.get(mPosition));
                    ((Activity)mContext).startActivityForResult(intent,Create1Activity.SELECT_NOTE_ACTIVITY_REQUEST_CODE);
                }
            });
        }
    }

}
