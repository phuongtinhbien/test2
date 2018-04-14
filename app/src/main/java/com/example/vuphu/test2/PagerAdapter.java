package com.example.vuphu.test2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class PagerAdapter extends FragmentPagerAdapter {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    List<schedule> schedules;
    Context context;
    List<Fragment> fragments = new ArrayList<>();


    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        sharedPreferences = context.getSharedPreferences("schedule", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        schedules = setData();
        for (int i=0;i<schedules.size();i++){
            fragments.add(ScheduleFragment.newInstance(schedules.get(i)));
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        if (position<fragments.size() && position>=0)
        return fragments.get(position);
        return null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    public List<schedule> setData() {
        List<schedule> schedules = new ArrayList<>();
        Gson gson = new Gson();

        Map<String, ?> map = sharedPreferences.getAll();
        List<String> key = new ArrayList<String>((Collection<? extends String>) map.values());
        for (int i = 0; i < key.size(); i++) {
            schedules.add(gson.fromJson(key.get(i).toString(), schedule.class));
        }
        Collections.sort(schedules, new Comparator<schedule>() {
            @Override
            public int compare(schedule schedule, schedule t1) {
                return schedule.getId() - t1.getId();
            }
        });
        return schedules;
    }

}
