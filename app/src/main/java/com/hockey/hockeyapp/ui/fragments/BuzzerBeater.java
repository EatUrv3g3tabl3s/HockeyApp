package com.hockey.hockeyapp.ui.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hockey.hockeyapp.R;

import java.util.concurrent.TimeUnit;

/**
 * Created by v3g3tabl3s on 7/30/14.
 */
public class BuzzerBeater extends Fragment {

    private Button btnStart, btnStop;
    private TextView textViewTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buzzer_beater, container, false);

        btnStart = (Button) rootView.findViewById(R.id.btnStart);
        btnStop = (Button) rootView.findViewById(R.id.btnStop);
        textViewTime = (TextView) rootView.findViewById(R.id.textViewTime);

        textViewTime.setText("");

        final CounterClass timer = new CounterClass(5000, 100);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            textViewTime.setText("0 seconds");
        }

        @Override
        public void onTick(long ms)
        {
            int secondsLeft = 0;
            if(Math.round((float)ms / 1000.0f) != secondsLeft)
            {
                secondsLeft = Math.round((float)ms / 1000.0f);
                textViewTime.setText(secondsLeft + "seconds");
            }

            Log.i("test","ms="+ms+" till finished="+secondsLeft );
        }
    }
}
