package yogasutra.android.com.patanjali;

import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class Chapter4 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MediaPlayer mediaPlayer;
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chap4_main);

        Toast toast = Toast.makeText(this,"KAIVALYA PAADA",Toast.LENGTH_SHORT);
        toast.show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ImageButton ply = (ImageButton) findViewById(R.id.play);
        ImageButton pse = (ImageButton) findViewById(R.id.pause);
        mediaPlayer = MediaPlayer.create(Chapter4.this, R.raw.kaivalya_paada);
        seekBar = (SeekBar) findViewById(R.id.seek);
        int total;

        total = mediaPlayer.getDuration();
        seekBar.setMax(total);
        Log.d("TOTAL", "run: mediaplayer total is " + total);
        MediaController controller = new MediaController(Chapter4.this);
        controller.show();



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
                Toast toast = Toast.makeText(Chapter4.this,"Playing",Toast.LENGTH_SHORT);
                toast.show();

                runOnUiThread(run);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        int Seek_Position = seekBar.getProgress();
                        mediaPlayer.seekTo(Seek_Position);


                    }
                });


            }
        });
        ReadShloka();


        pse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                Toast toast = Toast.makeText(Chapter4.this,"Pause",Toast.LENGTH_SHORT);
                toast.show();

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        try {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setType("plain/text");
                    sendIntent.setData(Uri.parse("test@gmail.com"));
                    sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"srisheshprabhu@gmail.com"});
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "test");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Please Edit the text with your Queries or suggestions");
                    startActivity(sendIntent);
                }
            });
        } catch (Resources.NotFoundException E ){
            Log.d("MAIL","there is a problem with mail intent");
            Toast.makeText(this,"Couldnt open mail",Toast.LENGTH_SHORT);
            toast.show();


        }

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
            Handler mHandler = new Handler();
            int CurrentPosition;


            if (mediaPlayer.isPlaying() && mediaPlayer != null) {


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
    protected void onStop() {
        super.onStop();
    }



    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mediaPlayer.stop();
            super.finish();

            //v super.onBackPressed();
        }


    }

    void ReadShloka() {
        InputStream IS = getApplicationContext().getResources().openRawResource(R.raw.chapter4);

        try {

            int size = IS.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            IS.read(buffer);
            IS.close();

            // Convert the buffer into a string.
            String text = new String(buffer);

            // Finally stick the string into the text view.
            TextView tv = (TextView) findViewById(R.id.shloka_1);
            tv.setText(text);
        } catch (IOException e) {

            //be carefull here
            throw new RuntimeException(e);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        mediaPlayer.pause();
        int id = item.getItemId();

        if (id == R.id.nav_intro) {
            Toast toast = Toast.makeText(this,"Opening INTRODUCTION",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(Chapter4.this,Introduction.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_chap1) {
            Toast toast = Toast.makeText(this,"Opening Chapter1(SAMAADHI PAADA)",Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(Chapter4.this,Chapter1.class);
            startActivity(intent);


        }
        else if (id == R.id.nav_chap2) {
            Toast toast = Toast.makeText(this,"Opening Chapter2(SAADHANA PAADA)",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(Chapter4.this,Chapter1.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_chap3) {
            Toast toast = Toast.makeText(this,"Opening Chapter3(VIBHUTI PAADA)",Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(Chapter4.this,Chapter1.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_chap4) {
            Toast toast = Toast.makeText(this,"Opening Chapter4(KAIVALYA PAADA)",Toast.LENGTH_SHORT);
            toast.show();

            Intent intent = new Intent(Chapter4.this,Chapter1.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

}




