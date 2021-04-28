package com.example.fingerspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView timer;
    private TextView taptextview;
    private Button tapButton;

    private CountDownTimer countDownTimer;
    private long initialCountdownInMillis = 60000;
    private int interval = 1000;
    private int remaining = 60;

    private int aThousand=1000;

    private final String REMAINING_TIME_KEY = "remaining_time_key";
    private final String A_THOUSAND = "a_thousand";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(REMAINING_TIME_KEY,remaining);
        outState.putInt(A_THOUSAND,aThousand);
        countDownTimer.cancel();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = findViewById(R.id.timerTextView);
        taptextview = findViewById(R.id.taptextview);
        tapButton = findViewById(R.id.tapbutton);

        taptextview.setText(aThousand+"");

        if(savedInstanceState !=null) {

            remaining = savedInstanceState.getInt(REMAINING_TIME_KEY);
            aThousand = savedInstanceState.getInt(A_THOUSAND);

            restoreTheGame();
        }

        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aThousand--;
                taptextview.setText(aThousand+"");
                if(remaining>0 && aThousand<=0) {
                    Toast.makeText(MainActivity.this, "Congrats", Toast.LENGTH_SHORT).show();
                    alertDialog("congratulations","please reset the game");
                }



            }
        });

        countDownTimer = new CountDownTimer(initialCountdownInMillis,interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                remaining = (int)millisUntilFinished/1000;
                timer.setText(remaining+"");
            }

            @Override
            public void onFinish() {

                    alertDialog("not good","try again?");

            }
        };

        countDownTimer.start();

    }

    private void restoreTheGame() {

        int restoreremainingtime = remaining;
        int restoreathousand = aThousand;

        timer.setText(restoreremainingtime+"");
        taptextview.setText(restoreathousand);

        countDownTimer =new CountDownTimer((long)remaining *1000,interval) {
            @Override
            public void onTick(long millisUntilFinished) {

                remaining=(int)millisUntilFinished/1000;
                timer.setText(remaining);
            }

            @Override
            public void onFinish() {

                alertDialog("finished","please restart the game!!");
            }
        };

        countDownTimer.start();
    }

    private void resetTheGame() {

        countDownTimer.cancel();
        aThousand = 1000;
        taptextview.setText(aThousand+"");
        timer.setText(remaining+"");

        countDownTimer = new CountDownTimer(initialCountdownInMillis,interval) {
            @Override
            public void onTick(long millisUntilFinished) {

                remaining = (int)millisUntilFinished/1000;
                timer.setText(remaining+"");
            }

            @Override
            public void onFinish() {

                alertDialog("you Lose","please reset the game!!");
            }
        };

        countDownTimer.start();
    }

    private void alertDialog(String title , String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle(title)
//set message
                .setMessage(message)
//set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        resetTheGame();

                    }
                })
//set negative button

                .show();
        alertDialog.setCancelable(false);
    }
}