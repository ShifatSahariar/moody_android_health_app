package com.example.moody777;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class IncomeFragment extends Fragment {


    sqlite_layer mdb;
    private RecyclerView recyclerView;
    private IncomeAdapter mAdapter;
    ArrayList<String> id, name, value, date;
    IncomeAdapter incomeAdapter;

    private TextView totalIncomeResult;


    public IncomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mdb = new sqlite_layer(this.getActivity());


    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_id_income);

        int result = 0;

        id = new ArrayList<>();
        name = new ArrayList<>();
        value = new ArrayList<>();
        date = new ArrayList<>();

        StoreDataInArray();

//
        totalIncomeResult = view.findViewById(R.id.income_txt_result);
        for (String temp : value) {
            result += Integer.parseInt(temp);
        }
        for (String temp1 : name) {
            System.out.println("nameLIST" + name);

        }

        totalIncomeResult.setText(String.valueOf(result));
        //
        System.out.println("id" + id);
        System.out.println("name" + name);

        incomeAdapter = new IncomeAdapter(getActivity(), id, name, value, date);
        recyclerView.setAdapter(incomeAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_income, container, false);
        return view;
    }

    void StoreDataInArray() {
        Cursor cursor = mdb.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                System.out.println("idc" + id);

                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                value.add(cursor.getString(2));
                date.add(cursor.getString(3));

            }
        }
    }


}