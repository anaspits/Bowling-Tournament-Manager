package com.example.bowlingchampionshipmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class PlayerandGamesAdapter extends RecyclerView.Adapter<PlayerandGamesAdapter.BowlingViewHolder>  {

    private final LayoutInflater layoutInflater;
    private Context mContext;
    //fixme private List<Object> mNotes;
    private List<PlayerandGames> mNotes;
    private Championship ch;


    public static List<Round> rounds = new ArrayList<>(); //test

    public PlayerandGamesAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public PlayerandGamesAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list3, parent, false);
        PlayerandGamesAdapter.BowlingViewHolder viewHolder = new PlayerandGamesAdapter.BowlingViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerandGamesAdapter.BowlingViewHolder holder, int position) {

        if (mNotes != null) {
            PlayerandGames note = mNotes.get(position);
            holder.setData(note.getLastName()+" "+note.getFirstName(),note.getBowlAvg(),note.getHdcp(),note.getGames(), position);
            //holder.setListeners();
        } else {
            // Covers the case of data not being ready yet.
            holder.txv2.setText(R.string.exit);
        }
    }


    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    public void setPlayers(List<PlayerandGames> notes) {
        mNotes = notes;
        notifyDataSetChanged();

    }

    public void setChamp(Championship champ) {
        ch=champ;
        System.out.println("champid = "+ ch.getFchampID()+" "+ch.getUuid());
    }

    public class BowlingViewHolder extends RecyclerView.ViewHolder {

        private TextView txv1,txv2,txv3,txv4;
        private int mPosition;

        public BowlingViewHolder(@NonNull View itemView) {
            super(itemView);
            txv1 = itemView.findViewById(R.id.txv1);
            txv2 	 = itemView.findViewById(R.id.txv2);
            txv3 = itemView.findViewById(R.id.txv3);
            txv4=itemView.findViewById(R.id.txv4);
        }

        public void setData(String name, float avg, int hdcp, int games, int position) {
            txv1.setText(name);
            NumberFormat f = new DecimalFormat("###.#");
            txv2.setText(String.valueOf(f.format(avg)));
            txv3.setText(String.valueOf(hdcp));
            txv4.setText(String.valueOf(games));
            mPosition = position;
        }
    }
}
