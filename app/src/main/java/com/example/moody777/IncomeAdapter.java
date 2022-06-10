package com.example.moody777;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList id,name,value,date;

    public IncomeAdapter( Context context, ArrayList id, ArrayList name, ArrayList value, ArrayList date) {
        this.context=context;
        this.id=id;
        this.name=name;
        this.value=value;
        this.date=date;


    }


    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.i_recyc, parent, false);
        return new IncomeAdapter.IncomeViewHolder(view);

    }
    @NonNull
    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, final int position) {


        System.out.println("name "+name.get(position));
        System.out.println("id "+id.get(position));

        holder.nameText.setText(String.valueOf(name.get(position)));
        holder.valueText.setText(String.valueOf(value.get(position)));
        holder.dateText.setText(String.valueOf(date.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpdateIncome.class);
                intent.putExtra("id",String.valueOf(id.get(position)));
                intent.putExtra("name",String.valueOf(name.get(position)));
                intent.putExtra("value",String.valueOf(value.get(position)));
                intent.putExtra("date",String.valueOf(date.get(position)));

                System.out.println("n_id "+id.get(position));
                System.out.println("n_name "+name.get(position));



                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class IncomeViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView valueText;
        public TextView dateText;
        LinearLayout mainLayout;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText=itemView.findViewById(R.id.type_txt_income);
            valueText=itemView.findViewById(R.id.ammount_txt_income);
            dateText=itemView.findViewById(R.id.date_txt_income);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}
