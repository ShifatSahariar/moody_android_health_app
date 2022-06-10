package com.example.moody777;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Budget  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;


    //Fragment

    private DashBoardFragment dashBoardFragment;
    private IncomeFragment incomeFragment;
    private ExpenseFragment expenseFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        this.setTitle("Budget");



        bottomNavigationView=findViewById(R.id.bottomNavigationbar);
        frameLayout=findViewById(R.id.main_frame);

        dashBoardFragment=new DashBoardFragment();
        incomeFragment=new IncomeFragment();
        expenseFragment=new ExpenseFragment();

        setFragment(dashBoardFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.dashboard:
                        setFragment(dashBoardFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        return true;

                    case R.id.income:
                        setFragment(incomeFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.income);
                        return true;

                    case R.id.expense:
                        setFragment(expenseFragment);
                        bottomNavigationView.setItemBackgroundResource(R.color.expense);
                        return true;

                    default:
                        return false;

                }
            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedListener(item.getItemId());
        return true;
    }

    private void displaySelectedListener(int itemId) {
        Fragment fragment=null;

        switch (itemId){
            case R.id.dashboard:
                fragment=new DashBoardFragment();
                break;

            case R.id.income:

                fragment=new IncomeFragment();

                break;

            case R.id.expense:
                fragment=new ExpenseFragment();
                break;

           // case R.id.logout:
               // mAuth.signOut();
            // startActivity(new Intent(getApplicationContext(),MainActivity.class));
             //   break;

        }
}}