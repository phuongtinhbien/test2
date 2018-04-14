package com.example.vuphu.test2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
public class TabPagerAdapter extends FragmentPagerAdapter {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    List<room> rooms;
    Context context;
    List<Fragment> fragments = new ArrayList<>();


    public TabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        sharedPreferences = context.getSharedPreferences("room", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rooms = setData();

        for (int i=0;i<rooms.size();i++){
            fragments.add(RoomFragment.newInstance(rooms.get(i)));
        }

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                return fragments.get(position);
            case 1:

                return fragments.get(position);
            case 2:
                return  fragments.get(position);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return rooms.get(position).getName();
    }

    public List<room> setData() {
        List<room> modes = new ArrayList<>();
        Gson gson = new Gson();
        Map<String, ?> map = sharedPreferences.getAll();
        List<String> key = new ArrayList<String>((Collection<? extends String>) map.values());
        for (int i = 0; i < key.size(); i++) {
            modes.add(gson.fromJson(key.get(i).toString(), room.class));
        }
        return modes;
    }
}
