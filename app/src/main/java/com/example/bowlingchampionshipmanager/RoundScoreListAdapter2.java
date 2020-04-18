package com.example.bowlingchampionshipmanager;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class RoundScoreListAdapter2 extends RecyclerView.Adapter<RoundScoreListAdapter2.MyViewHolder> {


        private LayoutInflater inflater;
        public static List<Participant> editModelArrayList;


        //public RoundScoreListAdapter2(Context ctx, ArrayList<Participant> editModelArrayList){
        public RoundScoreListAdapter2(Context ctx){
            inflater = LayoutInflater.from(ctx);
           // this.editModelArrayList = editModelArrayList;
        }

        @Override
        public RoundScoreListAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.roundlist, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final RoundScoreListAdapter2.MyViewHolder holder, final int position) {


            holder.editText.setText(String.valueOf(editModelArrayList.get(position).getHdcp()));
            Log.d("print","yes");

        }
    public void setBowls(List<Participant> notes) {
        editModelArrayList = notes;
        notifyDataSetChanged();

    }
        @Override
        public int getItemCount() {
            return editModelArrayList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            protected EditText editText;

            public MyViewHolder(View itemView) {
                super(itemView);

                editText = (EditText) itemView.findViewById(R.id.txvHDCP);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        editModelArrayList.get(getAdapterPosition()).setHdcp(Integer.parseInt(editText.getText().toString()));
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

            }

        }
    }
