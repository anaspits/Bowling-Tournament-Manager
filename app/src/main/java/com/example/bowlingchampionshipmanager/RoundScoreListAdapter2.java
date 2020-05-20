package com.example.bowlingchampionshipmanager;


import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


//gia team 1
public class RoundScoreListAdapter2 extends RecyclerView.Adapter<RoundScoreListAdapter2.MyViewHolder> {


        private LayoutInflater inflater;
        public static List<Participant> editModelArrayList;
        public static String[] edited = {"0","0","0"}; //na svisw
        public Round r;
        public static ArrayList<Round_detail> rd = new ArrayList<>();
        private static int position;



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
                holder.txvNote.setText(String.valueOf(editModelArrayList.get(position).getFullName()));
                if( editModelArrayList.get(position).getUuid().equals("blind")){ //an h omada exei ligoterous paiktes=blind
                    holder.cardview.setEnabled(false);
                    //cardview.setCardBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
                    holder.cardview.setCardBackgroundColor(Color.LTGRAY);
                    holder.txvNote.setText("BLIND");
                    holder.txvNote.setEnabled(false);
                    holder.first.setEnabled(false);
                    holder.first.setFocusable(false);
                    holder.second.setEnabled(false);
                    holder.second.setFocusable(false);
                    holder.third.setEnabled(false);
                    holder.third.setFocusable(false);
                    holder.hdcp.setEnabled(false);
                    holder.hdcp.setFocusable(false);
                    holder.first.setText("0");
                    holder.second.setText("0");
                    holder.third.setText("0");
                    holder.hdcp.setText("0");
                } else {
                    holder.hdcp.setText(String.valueOf(editModelArrayList.get(position).getHdcp()));
                    if (String.valueOf(rd.get(position).getFirst()) != null) {
                        holder.first.setText(String.valueOf(rd.get(position).getFirst()));
                    }
                    if (String.valueOf(rd.get(position).getSecond()) != null) {
                        holder.second.setText(String.valueOf(rd.get(position).getSecond()));
                    }
                    if (String.valueOf(rd.get(position).getThird()) != null) {
                        holder.third.setText(String.valueOf(rd.get(position).getThird()));
                    }
                }
                this.position=position;
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

    public void setBowls2(List<Participant> notes, Round round) { //todo test it
        editModelArrayList = notes;
        r = round;
        for (int i = 0; i < editModelArrayList.size(); i++) {
            Round_detail round_detail = new Round_detail(r.getRounduuid(), editModelArrayList.get(position).getUuid(), 0, 0, 0, editModelArrayList.get(position).getHdcp(), 0, r.getChampuuid(),r.getFroundid(), Calendar.getInstance().getTime()  );
            rd.add(round_detail);
            notifyDataSetChanged();
        }
    }

    public void setRound_detail(ArrayList<Round_detail> roundD) {
            rd=roundD;
    }
    public void setRound(Round round) {
        r=round;
        for (int i = 0; i < RoundScoreListAdapter2.editModelArrayList.size(); i++) { //todo afou to kanw ap'e3w, aw to svisw
            Round_detail round_detail= new Round_detail(r.getRounduuid(), editModelArrayList.get(i).getUuid(), 0, 0, 0,editModelArrayList.get(i).getHdcp(), 0, r.getChampuuid(),r.getFroundid(),Calendar.getInstance().getTime()  );
            System.out.println(" rounddetail: rid "+r.getFroundid()+" pid "+ editModelArrayList.get(i).getFirstName());
            rd.add(round_detail);
            notifyDataSetChanged();
        }
    }
        @Override
        public int getItemCount() {
            if (editModelArrayList != null)
                return editModelArrayList.size();
            else return 0;
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            protected EditText hdcp,first,second, third; //hdcp
            protected TextView txvNote;
            protected CardView cardview;

            public MyViewHolder(View itemView) {
                super(itemView);
                txvNote = itemView.findViewById(R.id.txvNote);
                hdcp = (EditText) itemView.findViewById(R.id.txvHDCP);
                first 	 = itemView.findViewById(R.id.txv1);
                second 	 = itemView.findViewById(R.id.txv2);
                third = itemView.findViewById(R.id.txv3);
                cardview=itemView.findViewById(R.id.roundcardview);

                //cardview.setOnClickListener((View.OnClickListener) this);
                    txvNote.setOnClickListener((View.OnClickListener) this);

                hdcp.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (hdcp.getText().toString().equals("")) {
System.out.println("hdcp keno");
                        } else {
                            editModelArrayList.get(getAdapterPosition()).setHdcp(Integer.parseInt(hdcp.getText().toString()));
                            rd.get(getAdapterPosition()).setHdcp(Integer.parseInt(hdcp.getText().toString()));
                            System.out.println("1)HDCP: player "+editModelArrayList.get(getAdapterPosition()).getUuid()+" "+getAdapterPosition()+editModelArrayList.get(getAdapterPosition()).getFullName()+" position "+getAdapterPosition()+ " hdcp "+editModelArrayList.get(getAdapterPosition()).getHdcp() );
                            System.out.println("2)HDCP rd: player "+rd.get(getAdapterPosition()).getParticipant_uuid()+" position "+getAdapterPosition()+" hdcp "+ rd.get(getAdapterPosition()).getHdcp() +" 1st " + RoundScoreListAdapter2.rd.get(getAdapterPosition()).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(getAdapterPosition()).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(getAdapterPosition()).getThird());
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
                        if (first.getText().toString().equals("")) {
                            System.out.println("on change");
                        } else {
                            System.out.println("on change "+first.getText().toString());
                            //edited[0]= first.getText().toString();
                            rd.get(getAdapterPosition()).setFirst(Integer.parseInt(first.getText().toString()));
                            System.out.println("1)FIRST: player "+editModelArrayList.get(getAdapterPosition()).getUuid()+" "+getAdapterPosition()+editModelArrayList.get(getAdapterPosition()).getFullName()+" position "+getAdapterPosition()+ " hdcp "+editModelArrayList.get(getAdapterPosition()).getHdcp() );
                            System.out.println("2)FIRST rd: player "+rd.get(getAdapterPosition()).getParticipant_uuid()+" position "+getAdapterPosition()+" hdcp "+ rd.get(getAdapterPosition()).getHdcp() +" 1st " + RoundScoreListAdapter2.rd.get(getAdapterPosition()).getFirst() + " 2nd " + RoundScoreListAdapter2.rd.get(getAdapterPosition()).getSecond() + " 3rd " + RoundScoreListAdapter2.rd.get(getAdapterPosition()).getThird());
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
                        if (second.getText().toString().equals("")) {
                            System.out.println("on change");
                        } else {
                           // edited[1]= second.getText().toString();
                            rd.get(getAdapterPosition()).setSecond(Integer.parseInt(second.getText().toString()));
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
                        if (third.getText().toString().equals("")) {
                            System.out.println("on change");
                        } else {
                            //edited[2]= third.getText().toString();
                            rd.get(getAdapterPosition()).setThird(Integer.parseInt(third.getText().toString()));
                        }
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                        //editModelArrayList.get(getAdapterPosition()).setHdcp(Integer.parseInt(hdcp.getText().toString()));
                    }
                });

            }
//gia blind
             @Override
            public void onClick(View v){
                int pos=getAdapterPosition();
                //clicklistener.onItemClick(pos);
                if(txvNote.getText().equals("BLIND")){
                    //cardview.setEnabled(true);
                    cardview.setCardBackgroundColor(Color.parseColor("#F9F9F9"));
                    //.setFocusable(true);
                    first.setEnabled(true);
                    first.setFocusableInTouchMode(true);
                    second.setEnabled(true);
                    second.setFocusableInTouchMode(true);
                    third.setEnabled(true);
                    third.setFocusableInTouchMode(true);
                    hdcp.setEnabled(true);
                    hdcp.setFocusableInTouchMode(true);
                    txvNote.setText(String.valueOf(editModelArrayList.get(pos).getFullName()));
                    hdcp.setText(String.valueOf(editModelArrayList.get(pos).getHdcp()));
                    if (String.valueOf(rd.get(pos).getFirst())!=null){
                        first.setText(String.valueOf(rd.get(pos).getFirst()));}
                    if (String.valueOf(rd.get(pos).getSecond())!=null) {
                        second.setText(String.valueOf(rd.get(pos).getSecond()));
                    }
                    if (String.valueOf(rd.get(pos).getThird())!=null) {
                        third.setText(String.valueOf(rd.get(pos).getThird()));
                    }
                    rd.get(getAdapterPosition()).setBlind(0);
                    System.out.println("NOT blind: "+editModelArrayList.get(pos).getFullName());
                }else {
                    //cardview.setEnabled(false);
                    //cardview.setCardBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
                    cardview.setCardBackgroundColor(Color.LTGRAY);
                    txvNote.setText("BLIND");
                    first.setEnabled(false);
                    first.setFocusable(false);
                    //first.setCursorVisible(false);
                    second.setEnabled(false);
                    second.setFocusable(false);
                    third.setEnabled(false);
                    third.setFocusable(false);
                    hdcp.setEnabled(false);
                    hdcp.setFocusable(false);
                    first.setText("0");
                    second.setText("0");
                    third.setText("0");
                    hdcp.setText("0");
                    rd.get(pos).setBlind(1);
                    System.out.println("blind: "+editModelArrayList.get(pos).getFullName()+" "+editModelArrayList.get(pos).getUuid()+" "+ rd.get(pos).getParticipant_uuid()+" "+rd.get(pos).getBlind());
                }
            }

        }
    }
