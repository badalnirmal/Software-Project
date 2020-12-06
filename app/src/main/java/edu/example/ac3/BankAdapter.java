package edu.example.ac3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

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
        textView.setText(String.format("%s\n%s away. \n", bank.getB_name(), bank.getDistance()));
        Button button = holder.selectbank;
        button.setText("Select Bank");

    }


    @Override
    public int getItemCount() {
        return banklist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView bankname;
        public Button selectbank;
        private Context context;
        public ViewHolder(Context context,View itemView){
            super(itemView);
            bankname = itemView.findViewById(R.id.bank_name);
            selectbank = itemView.findViewById(R.id.bank_select);
            this.context = context;
            itemView.setOnClickListener(this);//set the parameter to this if don't work

        }

        @Override
        public void onClick(View v) {
            int pos = getLayoutPosition();//Try get adapter posittion if dont work
            if(pos!= RecyclerView.NO_POSITION) {
                Bank1 bank_final= banklist.get(pos);
                Toast.makeText(context,String.valueOf(banklist.get(pos)),Toast.LENGTH_SHORT).show();
                Toast.makeText(context,bank_final.getB_name(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,AC5.class);
                intent.putExtra("bank_final",bank_final);
                context.startActivity(intent);
            }

        }
    }

}
