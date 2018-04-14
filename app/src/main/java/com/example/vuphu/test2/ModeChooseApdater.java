package com.example.vuphu.test2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Collection;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModeChooseApdater {

    private static class viewHolder extends RecyclerView.ViewHolder{

        CircleImageView bg;
        TextView text, name;
        Boolean check = false;
        CardView cardView;
        public viewHolder(View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.color_choose);
            text = itemView.findViewById(R.id.text_choose);
            name = itemView.findViewById(R.id.tv_name_mode_choose);
            cardView = itemView.findViewById(R.id.card);

        }
    }

    public static class  adapter extends RecyclerView.Adapter<viewHolder>{

        Context context;
        List<mode> list;
        String check ="";

        public adapter(Context context, List<mode> list) {
            this.context = context;
            this.list = list;

        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_mode_choose,parent, false);

            return new viewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
            holder.text.setText(list.get(position).getText());
            holder.name.setText(list.get(position).getName());
            holder.bg.setImageResource(list.get(position).getColor());

           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (check.isEmpty()) {
                       check = list.get(position).getName();
                       holder.cardView.setCardBackgroundColor(Color.parseColor("#fceabb"));
                   }
                   else {
                       check = "";
                       holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"));
                   }
               }
           });

        }

        public String getCheck(){
            return check;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
