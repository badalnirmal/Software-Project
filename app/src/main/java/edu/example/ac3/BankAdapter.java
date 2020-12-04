package edu.example.ac3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder> {

    private ArrayList<Bank1> banklist;

    public BankAdapter(ArrayList<Bank1> banks_final) {
        banklist = banks_final;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View bankview = inflater.inflate(R.layout.item_bank,parent,false);

        ViewHolder viewHolder = new ViewHolder(bankview);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BankAdapter.ViewHolder holder, int position) {

        Bank1 bank = banklist.get(position);

        TextView textView = holder.bankname;
        textView.setText(bank.getB_name());
        Button button = holder.selectbank;
        button.setText("Select Bank");
    }


    @Override
    public int getItemCount() {
        return banklist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView bankname;
        public Button selectbank;


        public ViewHolder(View itemView){
            super(itemView);

            bankname = itemView.findViewById(R.id.bank_name);
            selectbank = itemView.findViewById(R.id.bank_select);

        }
    }
}
