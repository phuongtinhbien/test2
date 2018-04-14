package com.example.vuphu.test2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class ModeApdater {

    private static class viewHolder extends RecyclerView.ViewHolder{

        CircleImageView bg;
        TextView text, name;
        ImageButton edit;
        public viewHolder(View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.color);
            text = itemView.findViewById(R.id.text);
            name = itemView.findViewById(R.id.tv_name_mode);
            edit = itemView.findViewById(R.id.btn_edit);
        }
    }

    public static class  adapter extends RecyclerView.Adapter<viewHolder>{

        Context context;
        List<mode> list;

        public adapter(Context context, List<mode> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_mode,parent, false);

            return new viewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
            holder.text.setText(list.get(position).getText());
            holder.name.setText(list.get(position).getName());
            holder.bg.setImageResource(list.get(position).getColor());

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    context.startActivity(new Intent(context, ModifyTemperatureActivity.class).putExtra("data",list.get(position)));
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    context.startActivity(new Intent(context, ModifyTemperatureActivity.class).putExtra("data",list.get(position)));
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
