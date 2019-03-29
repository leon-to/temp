package com.ece496;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.orm.SugarContext;

public class MainActivity extends AppCompatActivity {

    final Fragment fragment1 = new MonthViewFragment();
    final Fragment fragment2 = new DashboardFragment();
    final Fragment fragment3 = new NotificationsFragment();
    final Fragment fragment4 = new SettingsFragment();
    final WeekViewFragment frag_weekview = new WeekViewFragment();

    final FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(getApplicationContext());
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.main_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
//        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();
        fm.beginTransaction().add(R.id.main_container, frag_weekview, "1").commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().replace(R.id.main_container, frag_weekview).show(frag_weekview).commit();
                    return true;

                case R.id.navigation_dashboard:
                    fm.beginTransaction().replace(R.id.main_container, fragment2).show(fragment2).commit();
                    return true;

                case R.id.navigation_notifications:
                    fm.beginTransaction().replace(R.id.main_container, fragment3).show(fragment3).commit();
                    return true;

                case R.id.navigation_settings:
                    fm.beginTransaction().replace(R.id.main_container, fragment4).show(fragment4).commit();
                    return true;
            }

            return false;
        }
    };


}










