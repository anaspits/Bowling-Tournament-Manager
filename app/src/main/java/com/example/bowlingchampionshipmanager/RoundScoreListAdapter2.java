package com.example.bowlingchampionshipmanager;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;



public class RoundScoreListAdapter2 extends RecyclerView.Adapter<RoundScoreListAdapter2.MyViewHolder> {


        private LayoutInflater inflater;
        public static List<Participant> editModelArrayList;
        public static int[] edited = {0,0,0};


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
            if (editModelArrayList != null) {
                Participant note = editModelArrayList.get(position);
                //holder.setData(String.valueOf(note.getFullName()),note.getHdcp(), position);
                holder.hdcp.setText(String.valueOf(editModelArrayList.get(position).getHdcp()));
                Log.d("print","yes");
            } else {
                // Covers the case of data not being ready yet.
                //holder.noteItemView.setText(R.string.exit);
            }



        }
    public void setBowls(List<Participant> notes) {
        editModelArrayList = notes;
        notifyDataSetChanged();

    }
        @Override
        public int getItemCount() {
            if (editModelArrayList != null)
                return editModelArrayList.size();
            else return 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            protected EditText hdcp,first,second, third; //hdcp


            public MyViewHolder(View itemView) {
                super(itemView);

                hdcp = (EditText) itemView.findViewById(R.id.txvHDCP);
                first 	 = itemView.findViewById(R.id.txv1);
                second 	 = itemView.findViewById(R.id.txv2);
                third = itemView.findViewById(R.id.txv3);

                hdcp.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (hdcp.getText().toString().equals("")) {
System.out.println("on change");
                        } else {
                            editModelArrayList.get(getAdapterPosition()).setHdcp(Integer.parseInt(hdcp.getText().toString()));
                            System.out.println("on change 2: "+ hdcp.getText().toString());

                            //score //todo mhpws na to kanw san sunarthsh allou?


                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                        //editModelArrayList.get(getAdapterPosition()).setHdcp(Integer.parseInt(hdcp.getText().toString()));
                    }
                });

                first.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (hdcp.getText().toString().equals("")) {
                            System.out.println("on change");
                        } else {
                           //todo na kanw class pou na ta krataei auta?
                            edited[0]= Integer.parseInt(first.getText().toString());
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                        //editModelArrayList.get(getAdapterPosition()).setHdcp(Integer.parseInt(hdcp.getText().toString()));
                    }
                });

                second.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (hdcp.getText().toString().equals("")) {
                            System.out.println("on change");
                        } else {
                            edited[1]= Integer.parseInt(second.getText().toString());
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                        //editModelArrayList.get(getAdapterPosition()).setHdcp(Integer.parseInt(hdcp.getText().toString()));
                    }
                });

                third.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (hdcp.getText().toString().equals("")) {
                            System.out.println("on change");
                        } else {
                            edited[2]= Integer.parseInt(third.getText().toString());
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                        //editModelArrayList.get(getAdapterPosition()).setHdcp(Integer.parseInt(hdcp.getText().toString()));
                    }
                });



            }

        }
    }
