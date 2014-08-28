package com.hockey.hockeyapp.ui.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hockey.hockeyapp.R;
import com.hockey.hockeyapp.ui.adapters.TextWatcherAdapter;

import java.util.concurrent.TimeUnit;

/**
 * Created by v3g3tabl3s on 7/30/14.
 */
public class BuzzerBeater extends Fragment {

    private Button btnStart, btnStop;
    private EditText textViewTime;
    private int timeEntered;
    private CounterClass timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.buzzer_beater, container, false);

        btnStart = (Button) rootView.findViewById(R.id.btnStart);
        btnStop = (Button) rootView.findViewById(R.id.btnStop);
        textViewTime = (EditText) rootView.findViewById(R.id.textViewTime);

        timeEntered = Integer.parseInt(textViewTime.getText().toString());


        textViewTime.addTextChangedListener(new TextWatcherAdapter());



        System.out.println(timeEntered);


        //CounterClass timer = new CounterClass(timeEntered * 1000, 100);
       // final CounterClass timer = new CounterClass(100);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeEntered = Integer.parseInt(textViewTime.getText().toString());
              //  System.out.println(timeEntered);
                timer = new CounterClass(timeEntered * 1000, 100);
                timer.start();

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //timeEntered = Integer.parseInt(textViewTime.getText().toString());

                //CounterClass timer = new CounterClass(timeEntered*1000, 100);
                timer.cancel();
            }
        });

        return rootView;
    }

    public class CounterClass extends CountDownTimer
    {
        public CounterClass(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish()
        {
            textViewTime.setText("00");
        }

        @Override
        public void onTick(long ms)
        {
            int secondsLeft = 0;
            if(Math.round((float)ms / 1000.0f) != secondsLeft)
            {
                secondsLeft = Math.round((float)ms / 1000.0f);
                if(secondsLeft < 10)
                    textViewTime.setText("0" + String.valueOf(secondsLeft));
                else
                    textViewTime.setText(String.valueOf(secondsLeft));
            }

            Log.i("test","ms="+ms+" till finished="+secondsLeft );
        }
    }
}
