package com.example.moody777.Calender;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.moody777.R;

import java.util.ArrayList;

class DialogAdaptorStudent extends BaseAdapter {
    Activity activity;

    private Activity context;
    private ArrayList<Dialogpojo> alCustom;
    private String sturl;


    public DialogAdaptorStudent(Activity context, ArrayList<Dialogpojo> alCustom) {
        this.context = context;
        this.alCustom = alCustom;

    }

    @Override
    public int getCount() {
        return alCustom.size();

    }

    @Override
    public Object getItem(int i) {
        return alCustom.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.row_addapt, null, true);

        TextView sleepHours=(TextView)listViewItem.findViewById(R.id.sleepHours);
        TextView sleepTypeInfo=(TextView)listViewItem.findViewById(R.id.Sleep_Type_Info_Others);
        TextView moodInformation=(TextView)listViewItem.findViewById(R.id.moodInformation);
        TextView exerciseInformation=(TextView)listViewItem.findViewById(R.id.exerciseInformation);


        sleepHours.setText("You Slept "+alCustom.get(position).getTotalHours() + " Hours.");

        sleepTypeInfo.setText("And it was "+alCustom.get(position).getSleepType()+".\n"
                + "Suddenly Awake Time: " +alCustom.get(position).getAwakeNightTime()+".\n"
                + "Reason for Awake: " + alCustom.get(position).getAwakeType());

        moodInformation.setText("Mood was "+alCustom.get(position).getMoodWas() +".");
        exerciseInformation.setText("You made Exercise at : "+alCustom.get(position).getExTime() +"."
                +".\n" + "You workout : " +alCustom.get(position).getExType() +".");


        return  listViewItem;
    }

}

