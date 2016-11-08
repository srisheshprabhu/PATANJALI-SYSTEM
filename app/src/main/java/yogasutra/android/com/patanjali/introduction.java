package yogasutra.android.com.patanjali;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

public class introduction extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button ply, pse, stop, frwd, rwd;
    MediaPlayer mediaPlayer;
    int cur_pos = 0, total = 0;
    private SeekBar seekBar = null;
    private Handler mHandler;
    int CurrentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_main);
        Button ply = (Button) findViewById(R.id.play);
        Button pse = (Button) findViewById(R.id.pause);
        Button stop = (Button) findViewById(R.id.stop);
        Button rwd = (Button) findViewById(R.id.rewind);
        Button frwd = (Button) findViewById(R.id.forward);
        //mediaPlayer = MediaPlayer.create(introduction.this, R.raw.samaadhi_paada);
        mediaPlayer = MediaPlayer.create(introduction.this, R.raw.samaadhi_paada);

        seekBar = (SeekBar) findViewById(R.id.seek);
        total = mediaPlayer.getDuration();
        seekBar.setMax(total);
        Log.d("TOTAL", "run: mediaplayer total is " + total);
        mHandler = new Handler();


        ply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null && mediaPlayer.isPlaying()) return;
            /*    if (seekBar.getProgress() > 0) {
                    mediaPlayer.start();
                    return;
                }
               // mediaPlayer.start();
               seekBar.setProgress(0);
               // seekBar.setMax(mediaPlayer.getDuration()); */
                mediaPlayer.start();
                runOnUiThread(run);

            }
        });


        pse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);

                }


            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    Runnable run = new Runnable() {

        @Override
        public void run() {
            if (mediaPlayer.isPlaying() && mediaPlayer!=null) {
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int Seek_Position = seekBar.getProgress();
                        mediaPlayer.seekTo(Seek_Position);

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });


                CurrentPosition = mediaPlayer.getCurrentPosition();
                Log.d("RuNNING UI THREAD", "run: mp sb pos" + CurrentPosition);
                int seekBarpos = seekBar.getProgress();
                Log.d("SEEKBAR POS", "run: " + seekBarpos);
                seekBar.setProgress(CurrentPosition);

            }
            mHandler.postDelayed(this, 1000);

        }


    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

