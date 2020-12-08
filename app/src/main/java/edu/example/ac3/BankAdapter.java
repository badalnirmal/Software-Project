package edu.example.ac3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder>{

    private ArrayList<Bank1> banklist;

    public BankAdapter (ArrayList<Bank1> banks_final) {
        banklist = banks_final;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View bankview = inflater.inflate(R.layout.item_bank,parent,false);

        ViewHolder viewHolder = new ViewHolder(context,bankview);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BankAdapter.ViewHolder holder, int position) {

        Bank1 bank = banklist.get(position);

        TextView textView = holder.bankname;
        textView.setText(String.format("%s\n%s km away. \nService Charge: NRS.%s\n", bank.getB_name(), bank.getDistance(),bank.getSer_charge()));

    }


    @Override
    public int getItemCount() {
        return banklist.size();
    }


    //Swap Items here to take care of sorting and updating recyclerview
    public void swapItems(ArrayList<Bank1> contacts) {

        // compute diffs
        final ContactDiffCallback diffCallback = new ContactDiffCallback(this.banklist, contacts);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        // clear contacts and add
        this.banklist.clear();
        this.banklist.addAll(contacts);

        diffResult.dispatchUpdatesTo(this); // calls adapter's notify methods after diff is computed
    }





    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView bankname;
        private Context context;
        public ViewHolder(Context context,View itemView){
            super(itemView);
            bankname = itemView.findViewById(R.id.bank_name);
            this.context = context;
            itemView.setOnClickListener(this);//set the parameter to this if don't work

        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();//Try get adapter posittion if dont work
            if(pos!= RecyclerView.NO_POSITION) {
                Bank1 bank_final= banklist.get(pos);

                String USD = null,GBP = null,CNY = null ,EUR=null;

                //Code below is to parse the bank JSON file to get hash-map of currencies and their rates in NRS vs them

                String json= null;
                try {
                    InputStream file = context.getAssets().open("12_4.json");
                    int size = file.available();
                    byte[] buffer = new byte[size];
                    file.read(buffer);
                    file.close();
                    json = new String(buffer);//new String(buffer,"UTF-8");use this if don't work
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject obj = new JSONObject(json);
                    //the first array below parses the dates thus need to loop over this array for different days
                    JSONArray m_jArry = obj.getJSONObject("data").getJSONArray("payload");
                    //the second array below parses the currency rates for the date in the single iteration of array above
                    JSONArray m_jArry2=m_jArry.getJSONObject(0).getJSONArray("rates");

                    ArrayList<HashMap<String, String>> formList = new ArrayList<HashMap<String, String>>();
                    HashMap<String, String> m_li;

                    for (int i = 0; i < m_jArry2.length(); i++) {
                        JSONObject jo_inside = m_jArry2.getJSONObject(i);
                        final String iso3 = jo_inside.getJSONObject("currency").getString("iso3");
                        String rate = jo_inside.getString("buy");

                        //get all the rates for one bank for that day for all five currencies
                        //base currency is NRS
                        //all other currencies should be converted to NRS and then converted to other value.


                        if(iso3.equals("GBP"))
                            GBP=rate;
                        else if (iso3.equals("USD"))
                            USD=rate;
                        else if (iso3.equals("EUR"))
                            EUR=rate;
                        else if (iso3.equals("CNY"))
                            CNY=rate;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //When From is NRS and to is other currencies
                if(bank_final.getCurr_from_s().equals("NRS"))
                {
                    if(bank_final.getCurr_to_s().equals("EUR"))
                    {
                        bank_final.setCurr_to(1/Double.parseDouble(EUR)) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("USD"))
                    {
                        bank_final.setCurr_to(1/Double.parseDouble(USD)) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("GBP"))
                    {
                        bank_final.setCurr_to(1/Double.parseDouble(GBP)) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("CNY"))
                    {
                        bank_final.setCurr_to(1/Double.parseDouble(CNY)) ;
                        bank_final.setCurr_from(1.0);

                    }
                }

                //When from is Euro and to is other currencies
                if(bank_final.getCurr_from_s().equals("EUR"))
                {
                    double rate = Double.parseDouble(EUR);
                    if(bank_final.getCurr_to_s().equals("NRS"))
                    {
                        bank_final.setCurr_to(rate) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("USD"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(USD) ) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("GBP"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(GBP) ) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("CNY"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(CNY)) ;
                        bank_final.setCurr_from(1.0);

                    }
                }
                //When from is USD and to is other currencies
                if(bank_final.getCurr_from_s().equals("USD"))
                {
                    double rate = Double.parseDouble(USD);
                    if(bank_final.getCurr_to_s().equals("NRS"))
                    {
                        bank_final.setCurr_to(rate) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("EUR"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(EUR) ) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("GBP"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(GBP)) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("CNY"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(CNY)) ;
                        bank_final.setCurr_from(1.0);

                    }
                }
                //When from is Chinese Yuan and to is other currencies
                if(bank_final.getCurr_from_s().equals("CNY"))
                {
                    double rate = Double.parseDouble(CNY);
                    if(bank_final.getCurr_to_s().equals("NRS"))
                    {
                        bank_final.setCurr_to(rate) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("EUR"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(EUR) ) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("GBP"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(GBP)) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("USD"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(USD) ) ;
                        bank_final.setCurr_from(1.0);

                    }
                }

                //When from is Great Britian Pound and to is other currencies
                if(bank_final.getCurr_from_s().equals("GBP"))
                {
                    double rate = Double.parseDouble(GBP);
                    if(bank_final.getCurr_to_s().equals("NRS"))
                    {
                        bank_final.setCurr_to(rate) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("EUR"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(EUR)) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("USD"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(USD)) ;
                        bank_final.setCurr_from(1.0);

                    }
                    if(bank_final.getCurr_to_s().equals("CNY"))
                    {
                        bank_final.setCurr_to(rate/Double.parseDouble(CNY)) ;
                        bank_final.setCurr_from(1.0);

                    }
                }

                //System.out.println(bank_final.getCurr_from_s() +" "+bank_final.getCurr_from() +"->"+  bank_final.getCurr_to_s() + " "+bank_final.getCurr_to());

                Intent intent = new Intent(context,AC5.class);
                intent.putExtra("bank_final",bank_final);
                intent.putExtra("from_s",bank_final.curr_from_s);
                intent.putExtra("to_s",bank_final.curr_to_s);
                context.startActivity(intent);
            }

        }
    }

}
