 package com.example.multi_notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Main_Adapter main_adapter;
    private ArrayList<Information> arrayList;
    private PreferenceManager preferenceManager;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView  = findViewById(R.id.rv_main);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        /*툴바*/
        toolbar = (Toolbar)findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);//커스텀액션바사용
        actionBar.setDisplayShowTitleEnabled(false);//기본제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기 기능생성
        //데이터 가져오기
        preferenceManager = new PreferenceManager();
        arrayList = preferenceManager.getInfo(getApplicationContext(),"all");

        //Set recyclerview
        main_adapter = new Main_Adapter(arrayList);
        recyclerView.setAdapter(main_adapter);
        main_adapter.setitemClickLister(new Main_Adapter.ItemClickListener_custom() {
            @Override
            public void onSwitchPressed(int pos,boolean b) {
                int hour = arrayList.get(pos).getHour();
                int min = arrayList.get(pos).getMinute();
                Calendar c  = Calendar.getInstance();
                c.set();
                if(arrayList.get(pos).getOnoff()){
                    arrayList.get(pos).setOnoff(false);

                }
                else{
                    arrayList.get(pos).setOnoff(true);
                }
                preferenceManager.setInfo(getApplicationContext(),"all",arrayList);
            }

            @Override
            public void onLongClickListener(int pos) {
                //TODO : 수정
            }

            @Override
            public void onCompleteListener(int pos) {
                //TODO : 완료되면 이미지나 회색으로 바꿔서 표시해주기
            }


        });

    }




    private void startAlarm(Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,createID(), intent, 0);
        if(c.before((Calendar.getInstance()))){
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1*60*1000 ,  pendingIntent);
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);

    }
     public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.KOREA).format(now));
         return id;
    }
     @Override
    public boolean onCreateOptionsMenu(@NonNull @NotNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
             case R.id.action_btn_add:
                TimePickerCustomDialog timePicker = new TimePickerCustomDialog(MainActivity.this);
                timePicker.setOnButtonClickListener(new TimePickerCustomDialog.OnButtonClickListener() {
                    @Override
                    public void onPositiveClicked(String title, int hour, int minute) {
                        //TODO: 알람 여러개 아이디 생성하는법, 그리고 알람 여러개를 알약별로 같은시간에 할수있게 할건지 이름만 할건지 생각해봐야될듯 완료여부를 한 시간안에 따로 할수있는지 아니면 미룰 수 있는지 ??
                        Information information = new Information(hour,minute,title,true);
                        arrayList.add(information);
                        preferenceManager.setInfo(getApplicationContext(),"all",arrayList);
                        main_adapter.notifyDataSetChanged();
                    }
                });
                timePicker.show();
                 //TODO : 리싸이클러뷰랑 연동
                return true;
            case R.id.action_btn_edit:
                //TODO : LongClickListener
                //알람취소
                // cancelAlarm(); 하면 끝

        }
        return super.onOptionsItemSelected(item);
    }



}

   /*
   다수알람 울리는법
   Intent intent = new Intent(act, 리시버.class);
       intent.putExtra("type",type);
       intent.setAction("timer");
   PendingIntent sender = PendingIntent.getBroadcast(getApplicationContext(), HoUtils.createID(), intent, PendingIntent.FLAG_UPDATE_CURRENT);*/
