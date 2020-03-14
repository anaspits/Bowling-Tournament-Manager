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
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

public class BowlingListAdapter extends RecyclerView.Adapter<BowlingListAdapter.BowlingViewHolder>  {

    public interface OnDeleteClickListener {
        void OnDeleteClickListener(Test_table myNote);
    }

    private final LayoutInflater layoutInflater;
    private Context mContext;
    private List<Test_table> mNotes;
    private OnDeleteClickListener onDeleteClickListener;

    public BowlingListAdapter(Context context, OnDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
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
            Test_table note = mNotes.get(position);
            holder.setData(note.getName(), position);
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

    public void setBowls(List<Test_table> notes) {
        mNotes = notes;
        notifyDataSetChanged();

    }

    public class BowlingViewHolder extends RecyclerView.ViewHolder {

        private TextView noteItemView;
        private int mPosition;
        private Button btDelete, btEdit;

        public BowlingViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            btDelete 	 = itemView.findViewById(R.id.ivRowDelete);
            btEdit 	 = itemView.findViewById(R.id.ivRowEdit);
        }

        public void setData(String note, int position) {
            noteItemView.setText(note);
            mPosition = position;
        }

        public void setListeners() {
            btEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditActivity.class);
                    intent.putExtra("bowlId", mNotes.get(mPosition).getId());
                    ((Activity)mContext).startActivityForResult(intent, Create1Activity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
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