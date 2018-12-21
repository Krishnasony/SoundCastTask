package i.krishnasony.souncasttask.Activities.PlayMusic;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import i.krishnasony.souncasttask.R;
import i.krishnasony.souncasttask.SongsListActivity;


public class PlayMusicActivity extends AppCompatActivity {
    TextView textViewtitle;
    ImageView imageViewthumbnail, next, previous, play;
    SeekBar seekBar;
    Uri uri;
    MediaPlayer mediaPlayer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        init();
        // backbutton toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

        Intent intent = getIntent();
        String title = intent.getStringExtra("Songname");
        textViewtitle.setText(title);
        Glide.with(this)
                .load(intent.getStringExtra("songThumbnail"))
                .into(imageViewthumbnail);
        uri = Uri.parse(intent.getStringExtra("link"));
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(PlayMusicActivity.this, uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare(); //don't use prepareAsync for mp3 playback
        } catch (IOException e) {
            e.printStackTrace();
        }
        seekBar.setMax(mediaPlayer.getDuration());

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer.isPlaying()){

                    Toast.makeText(PlayMusicActivity.this, "uri" + uri, Toast.LENGTH_SHORT).show();
                        mediaPlayer.start();


                    new Timer().scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        }
                    }, 0, 1000);

                        play.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);


                }
                else {
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);

                }



            }
        });

//manually seekbar change
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayer!=null&&b){
                    mediaPlayer.seekTo(i);
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void init() {
        textViewtitle = findViewById(R.id.song_title);
        imageViewthumbnail = findViewById(R.id.song_thumbnail);
        play = findViewById(R.id.play_or_pause);
        previous = findViewById(R.id.previous_play);
        next = findViewById(R.id.next_play);
        seekBar = findViewById(R.id.seekbar);
        toolbar = findViewById(R.id.back_toolbar);



    }





}



