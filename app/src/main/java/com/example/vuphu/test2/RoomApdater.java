package com.example.vuphu.test2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomApdater {

    private static class viewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView temp;



        public viewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_room);
            temp = itemView.findViewById(R.id.tv_temp_room);
        }
    }

    public static class  adapter extends RecyclerView.Adapter<viewHolder>{

        Activity context;
        List<room> list;
        private Dialog dialog;

        public adapter(Activity context, List<room> list) {
            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_room,parent, false);


            return new viewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {

            holder.name.setText(list.get(position).getName());

            holder.temp.setText(list.get(position).getTemp()+"°C ");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemListDialogFragment bottomSheetDialogFragment = new ItemListDialogFragment();

                    bottomSheetDialogFragment.newInstance(false,position+"").show(((FragmentActivity) context).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                }
            });

        }

        public  List<room> getList(){
            return list;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
