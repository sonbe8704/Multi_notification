package com.example.multi_notification;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.sql.Timestamp;
import java.util.Calendar;

public class TimePickerCustomDialog extends Dialog {

    private OnButtonClickListener onButtonClickListener;
    private EditText et_title;
    private TimePicker tp_content;
    private TextView tv_confirm,tv_cancle;

    public TimePickerCustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_timepicker);
        et_title = findViewById(R.id.et_title);
        tp_content = findViewById(R.id.tp_content);
        tv_confirm  =findViewById(R.id.tv_confirm);
        tv_cancle = findViewById(R.id.tv_cancle);


        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                //TODO : Change the way getting title from edittext to spinner
                String title = et_title.getText().toString();
                int hour = tp_content.getHour();
                int minute = tp_content.getMinute();

                onButtonClickListener.onPositiveClicked(title,hour,minute);
                dismiss();
            }
        });

        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
    public void setOnButtonClickListener(OnButtonClickListener  onButtonClickListener){
        this.onButtonClickListener = onButtonClickListener;
    }

    interface OnButtonClickListener{
        void onPositiveClicked(String title,int hour,int minute);
    }


}