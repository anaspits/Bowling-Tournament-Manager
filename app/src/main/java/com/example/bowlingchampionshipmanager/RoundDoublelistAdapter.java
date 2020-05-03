package com.example.bowlingchampionshipmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoundDoublelistAdapter  extends RecyclerView.Adapter<com.example.bowlingchampionshipmanager.RoundDoublelistAdapter.BowlingViewHolder>   {
        private final LayoutInflater layoutInflater;
        private Context mContext;
        private List<Round> mNotes;
        private Championship ch;

        public RoundDoublelistAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
            mContext = context;
        }
        @NonNull
        @Override
        public com.example.bowlingchampionshipmanager.RoundDoublelistAdapter.BowlingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.doublelist, parent, false);
            com.example.bowlingchampionshipmanager.RoundDoublelistAdapter.BowlingViewHolder viewHolder = new com.example.bowlingchampionshipmanager.RoundDoublelistAdapter.BowlingViewHolder(itemView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull com.example.bowlingchampionshipmanager.RoundDoublelistAdapter.BowlingViewHolder holder, int position) {

            if (mNotes != null) {
                Round note = mNotes.get(position);
                 //if (ch.getType()==2) {
                    holder.setData("Round:"+note.getFroundid(),"TEAM:" + note.getTeam1ID(), " TEAM:" + note.getTeam2ID(),note.getScore1(),note.getScore2(), position); //todo na to kanw me name ths team
               /* } else if (ch.getType()==1){
                    holder.setData("Round:"+note.getFroundid(),"TEAM:" + note.getTeam1ID(), "", position);
                }*/
               // holder.setListeners();
            } else {
                // Covers the case of data not being ready yet.
                holder.up1.setText(R.string.exit);
            }
        }

        @Override
        public int getItemCount() {
            if (mNotes != null)
                return mNotes.size();
            else return 0;
        }

        public void setSelRound(List<Round> notes) {
            mNotes = notes;
            notifyDataSetChanged();

        }

        public void setChamp(Championship champ) {
            ch = champ;
            System.out.println("selroundadapter champid = " + ch.getFchampID() + " " + ch.getUuid()+" "+ch.getStatus());
        }

        public class BowlingViewHolder extends RecyclerView.ViewHolder {

            private TextView up1,down1, up2,down2,up3,down3;
            private int mPosition;

            public BowlingViewHolder(@NonNull View itemView) {
                super(itemView);
                up1 = itemView.findViewById(R.id.txvup1);
                up2 = itemView.findViewById(R.id.txvup2);
                up3 = itemView.findViewById(R.id.txvup3);
                down1 = itemView.findViewById(R.id.txvdown1);
                down2= itemView.findViewById(R.id.txvdown2);
                down3= itemView.findViewById(R.id.txvdown3);

            }

            public void setData(String u1, String u2,String d2,int u3, int d3, int position) {
                up1.setText(u1);
                up2.setText(u2);
                mPosition = position;
                up3.setText(String.valueOf(u3));
                if (ch.getType() == 2) {
                    down2.setText(d2);
                    down3.setText(String.valueOf(d3));
                }
            }
            }

    }
