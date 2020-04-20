package com.example.bowlingchampionshipmanager;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
//na svisw
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
        View itemView = layoutInflater.inflate(R.layout.roundlist, parent, false);
        RoundScoreListAdapter.BowlingViewHolder viewHolder = new RoundScoreListAdapter.BowlingViewHolder(itemView, new MyCustomEditTextListener());
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
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
        holder.mEditText.setText((CharSequence) String.valueOf( mNotes.get(holder.getAdapterPosition()).getHdcp()));
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
        public EditText hdcp, first,second, third;
        private int mPosition;
        public EditText mEditText;
        public MyCustomEditTextListener myCustomEditTextListener;

        public BowlingViewHolder(@NonNull View itemView,MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            //hdcp 	 = itemView.findViewById(R.id.txvHDCP);
            first 	 = itemView.findViewById(R.id.txv1);
            second 	 = itemView.findViewById(R.id.txv2);
            third = itemView.findViewById(R.id.txv3);
            this.mEditText = (EditText)  itemView.findViewById(R.id.txvHDCP);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.mEditText.addTextChangedListener(myCustomEditTextListener);
        }

        public void setData(String note, int phdcp, int position) {
            noteItemView.setText(note);
            mEditText.setText(String.valueOf(phdcp));
            mPosition = position;
        }

    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
          mNotes.get(position).setHdcp(Integer.parseInt(charSequence.toString()));
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}
