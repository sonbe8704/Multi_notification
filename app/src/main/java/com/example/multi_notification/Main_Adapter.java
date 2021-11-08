package com.example.multi_notification;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class Main_Adapter extends RecyclerView.Adapter<Main_Adapter.CustomViewHolder> {
    private ArrayList<Information> arrayList;
    private ItemClickListener_custom itemClickListener_custom = null;
    public Main_Adapter() {
    }

    public Main_Adapter(ArrayList<Information> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tv_title.setText(arrayList.get(position).getName());
        int hour= arrayList.get(position).getHour();
        int minute = arrayList.get(position).getMinute();
        boolean onoff = arrayList.get(position).getOnoff();

        String content;
        content=Integer.toString(hour)+"시 "+Integer.toString(minute)+"분";
        holder.tv_content.setText(content);
        holder.itemView.setTag(position);

        holder.sw.setChecked(arrayList.get(position).getOnoff());
        if(!arrayList.get(position).getOnoff()){
            holder.iv_icon.setImageResource(R.drawable.ic_baseline_alarm_off_24);
        }
        holder.sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                itemClickListener_custom.onSwitchPressed(holder.getAdapterPosition(),b);
                if(!compoundButton.isChecked()){
                    holder.iv_icon.setImageResource(R.drawable.ic_baseline_alarm_off_24);
                }
                else{
                    holder.iv_icon.setImageResource(R.drawable.ic_baseline_access_alarms_24);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null!=arrayList ? arrayList.size():0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_title;
        protected TextView tv_content;
        protected ImageView iv_icon;
        protected Switch sw;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_content = itemView.findViewById(R.id.tv_content);
            this.sw = itemView.findViewById(R.id.sw);
            this.iv_icon = itemView.findViewById(R.id.iv_icon);
        }

    }



    public interface ItemClickListener_custom {
        void onSwitchPressed(int pos,boolean b);
        void onLongClickListener(int pos);
        void onCompleteListener(int pos);
    }
    public void setitemClickLister(ItemClickListener_custom itemClickListener_custom){
        this.itemClickListener_custom= itemClickListener_custom;
    }

}
