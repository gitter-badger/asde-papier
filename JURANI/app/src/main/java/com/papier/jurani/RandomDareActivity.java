package com.papier.jurani;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Angellica on 3/23/2016.
 */
public class RandomDareActivity extends AppCompatActivity {

    private int i = 0;
    private String [] player;
    private String [] dare = {"Impersonate...", "Tembak...", "Putusin...", "Cabut bulu kaki kanannya...",
                                "Pakaikan lipstik ke...", "Peluk erat...", "Selama 5 menit, gendong...",
                                "Gelitikin pinggang...", "Ikutin gerak-gerik...", "Terima pukulan dari...",
                                "Rayu atau gombalin...", "Pukul kencang tangan...", "Ajak kencan si...",
                                "Sampai makanannya abis, suapin...", "Nyanyiin lagu untuk...",
                                "Sampai game selesai, pegang tangan...", "Bacain puisi untuk..."};
    private String chosenPlayer;
    private String chosenDare;
    private TextView randDare;
    private Button stop;
    private Thread runThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_dare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.random_dare_toolbar);
        setSupportActionBar(toolbar);

        player = getIntent().getStringArrayExtra("player");

        chosenPlayer = getIntent().getExtras().getString("chosen");

        randDare = (TextView) findViewById(R.id.random_dare);
        Runnable runName = new Runnable() {
            @Override
            public void run() {
                while (i < dare.length) {
                    try {
                        Thread.sleep(10);
                        randDare.post(new Runnable() {
                            @Override
                            public void run() {
                                randDare.setText(dare[i]);
                                if (i == dare.length-1) {
                                    i = 0;
                                } else {
                                    i += 1;
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        runThread = new Thread(runName);
        runThread.start();

        stop = (Button) findViewById(R.id.stop_button_random_dare);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runThread.interrupt();
                chosenDare = randDare.getText().toString();
                Intent goChoose = new Intent(getApplicationContext(), RandomTargetActivity.class);
                goChoose.putExtra("player", player);
                goChoose.putExtra("chosen", chosenPlayer);
                goChoose.putExtra("task", chosenDare);
                startActivity(goChoose);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            Intent goToHelp = new Intent (getApplicationContext(), HelpActivity.class);
            startActivity(goToHelp);
            return true;
        } else if (id == R.id.action_developer) {
            Intent goToDev = new Intent (getApplicationContext(), DeveloperActivity.class);
            startActivity(goToDev);
            return true;
        } else if (id == R.id.action_reset) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Apakah kamu yakin ingin me-reset permainan?").setTitle("Reset");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent goToFirst = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(goToFirst);
                    finish();
                }
            });
            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    closeContextMenu();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
