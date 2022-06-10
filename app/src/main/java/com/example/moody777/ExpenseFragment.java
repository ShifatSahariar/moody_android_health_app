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


public class ExpenseFragment extends Fragment {


    sqlite_layer mdb;
    private RecyclerView recyclerView;
    private ExpenseAdapter mAdapterE;
    ArrayList<String> id, name, value, date;
    ExpenseAdapter expenseAdapter;

    private TextView totalExpenseResult;


    public ExpenseFragment() {
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
        View viewe = inflater.inflate(R.layout.fragment_expense, container, false);
        recyclerView = (RecyclerView) viewe.findViewById(R.id.recycler_id_expense);

        int result = 0;

        id = new ArrayList<>();
        name = new ArrayList<>();
        value = new ArrayList<>();
        date = new ArrayList<>();

        StoreDataInArray();

//
        totalExpenseResult = viewe.findViewById(R.id.expense_txt_result);
        for (String temp : value) {
            result += Integer.parseInt(temp);
        }

        totalExpenseResult.setText(String.valueOf(result));
        //
        System.out.println("id" + id);
        System.out.println("name" + name);

        expenseAdapter = new ExpenseAdapter(getActivity(), id, name, value, date);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_income, container, false);
        return viewe;
    }

    void StoreDataInArray() {
        Cursor cursorE = mdb.readAllDataE();
        if (cursorE.getCount() == 0) {
            Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursorE.moveToNext()) {
                System.out.println("idc" + id);

                id.add(cursorE.getString(0));
                name.add(cursorE.getString(1));
                value.add(cursorE.getString(2));
                date.add(cursorE.getString(3));

            }
        }
    }


}