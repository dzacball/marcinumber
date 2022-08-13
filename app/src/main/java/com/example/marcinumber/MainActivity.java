package com.example.marcinumber;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public int nyerobuttonid;
    public int[] ids = {R.id.button1, R.id.button2, R.id.button3};
    public int total = 0;
    public int hit = 0;
    public int a_min = 0;
    public int a_max = 99;
    public int b_min = 0;
    public int b_max = 99;
    public int minutes = 0;
    public Calendar startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView text = (TextView) findViewById(R.id.winstate);
        TextView remaining = (TextView) findViewById(R.id.remaining);
        text.setText("");
        TextView results = (TextView) findViewById(R.id.results);
        results.setText("0 / 0\n0%");
        showTimeDialog();

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    Calendar nowTime = Calendar.getInstance();
                    long milliseconds1 = startTime.getTimeInMillis();
                    long milliseconds2 = nowTime.getTimeInMillis();

                    long diff = milliseconds2 - milliseconds1;
                    long remain = TimeUnit.MINUTES.toMillis(minutes) - diff;
                    int remainsecs = (int) TimeUnit.MILLISECONDS.toSeconds(remain);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (remainsecs >= 0) {
                                remaining.setText(String.valueOf(remainsecs));
                            }
                        }
                    });
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();

        startTime = Calendar.getInstance();
        newQuest();

    }

    private void showTimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setKeyListener(new DigitsKeyListener());
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                minutes = Integer.valueOf(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void onClick(View v) {
        TextView text = (TextView) findViewById(R.id.winstate);
        Button button = (Button) findViewById(v.getId());
        Handler handler = new Handler();
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);

        button1.setEnabled(false);
        button2.setEnabled(false);
        button3.setEnabled(false);
        if (v.getId() == ids[nyerobuttonid]) {
            text.setText("IGEEEN!");

            button.setBackgroundColor(Color.GREEN);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    TextView haha = (TextView) findViewById(R.id.haha1);
                    haha.setText(haha.getText() + " = " + button.getText());

                }
            }, 500);


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    text.setText("");
                    button.setBackgroundColor(Color.BLUE);
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                    Calendar nowTime = Calendar.getInstance();
                    long milliseconds1 = startTime.getTimeInMillis();
                    long milliseconds2 = nowTime.getTimeInMillis();

                    long diff = milliseconds2 - milliseconds1;
                    //long diffminutes = TimeUnit.MILLISECONDS.toMinutes(diff);
                    if (diff > TimeUnit.MINUTES.toMillis(minutes)) {
                        button1.setVisibility(View.GONE);
                        button2.setVisibility(View.GONE);
                        button3.setVisibility(View.GONE);
                        ((TextView) findViewById(R.id.haha1)).setVisibility(View.GONE);
                        text.setText("END");
                    } else {
                        newQuest();
                    }
                }
            }, 4000);
            hit++;
        } else {
            text.setText("NEM NEM!");
            button.setBackgroundColor(Color.RED);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button.setBackgroundColor(Color.BLUE);
                    text.setText("");
                    button1.setEnabled(true);
                    button2.setEnabled(true);
                    button3.setEnabled(true);

                }
            }, 2000);

        }
        total++;
        TextView results = (TextView) findViewById(R.id.results);
        results.setText(String.valueOf(hit) + " / " + String.valueOf(total)+"\n"+String.valueOf((int) Math.round((double)hit/total*100)) + "%");
    }

    private int getRandNotEqual(ArrayList<Integer> e) {
        Random rand = new Random();

        while(true) {
            int a = rand.nextInt(a_max - a_min + 1) + a_min;
            int b = rand.nextInt(b_max - b_min + 1) + b_min;
            if (!e.contains(a+b)) {
                return a+b;
            }
        }
    }

    private void newQuest() {

        Random rand = new Random();
        int a = 0;
        int b = 0;
        CheckBox game_mode = (CheckBox) findViewById(R.id.simplecheck);
        CheckBox minus_mode = (CheckBox) findViewById(R.id.minuscheck);
        if (game_mode.isChecked()) {
            b_min = 1;
            b_max = 5;
        }
        a = rand.nextInt(a_max - a_min + 1) + a_min;
        b = rand.nextInt(b_max - b_min + 1) + b_min;
        nyerobuttonid = rand.nextInt(2 - 0 + 1) + 0;

        TextView text = (TextView) findViewById(R.id.haha1);
        int decide = rand.nextInt(25 - 0 + 1) + 0;
        if (decide > 23) {
            b = 0;
        }
        int nyeroszam = 0;
        if (minus_mode.isChecked() && ( (decide & 1) == 0 )) {
            text.setText(String.valueOf(a) + " - " + String.valueOf(b));
            nyeroszam = a - b;
        } else {
            text.setText(String.valueOf(a) + " + " + String.valueOf(b));
            nyeroszam = a + b;
        }
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        button1.setBackgroundColor(Color.BLUE);
        button2.setBackgroundColor(Color.BLUE);
        button3.setBackgroundColor(Color.BLUE);

        button1.setTextColor(Color.WHITE);
        button2.setTextColor(Color.WHITE);
        button3.setTextColor(Color.WHITE);

        Button nyerobutton = (Button) findViewById(ids[nyerobuttonid]);

        ArrayList<Integer> eddigiek = new ArrayList<Integer>();

        eddigiek.add(nyeroszam);
        int tmp;
        tmp = getRandNotEqual(eddigiek);
        button1.setText(String.valueOf(tmp));
        eddigiek.add(tmp);

        tmp = getRandNotEqual(eddigiek);
        button2.setText(String.valueOf(tmp));
        eddigiek.add(tmp);

        tmp = getRandNotEqual(eddigiek);
        button3.setText(String.valueOf(tmp));
        eddigiek.add(tmp);

        nyerobutton.setText(String.valueOf(nyeroszam));


        button1.setOnClickListener(this::onClick);
        button2.setOnClickListener(this::onClick);
        button3.setOnClickListener(this::onClick);


    }
}