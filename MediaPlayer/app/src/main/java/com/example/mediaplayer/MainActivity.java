package com.example.mediaplayer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    Spinner spinner;
    ArrayAdapter spinnerAdapter;
    MediaPlayer mediaPlayer;
    ImageView playPauseIcon;
    SeekBar seekBar;
    ImageView album_art;
    TextView album, artist, genre;
    MediaMetadataRetriever metaRetriver;
    byte[] art;
ArrayList<String>mp3s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playPauseIcon = findViewById(R.id.playIconImageView);
       mp3s=new ArrayList<String>();
        Field[] drawablesFields = com.example.mediaplayer.R.raw.class.getFields();
        for (Field  file : drawablesFields) {
               mp3s.add(file.getName());
            }
        Uri uri=Uri.parse("android.resource://com.example.project/"+"R.drawable."+mp3s.get(0));

        mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
        createSpinner();
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);

    //    album_art = (ImageView) findViewById(R.id.imageViewLogoNote);
//        metaRetriver = new MediaMetadataRetriever();
//        Uri myUri = Uri.parse("file:///sdcard/mp3/dzidzo.mp3");
//
////
//        try {
//            metaRetriver.setDataSource("/SDCARD/mp3/dzidzo.mp3");
//            art = metaRetriver.getEmbeddedPicture();
//            Bitmap songImage = BitmapFactory.decodeByteArray(art, 0, art.length);
//            album_art.setImageBitmap(songImage);
////            album.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
////            artist.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
////            genre.setText(metaRetriver .extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
//        }
//
//        catch (Exception e) { album_art.setBackgroundColor(Color.GRAY);
//        album.setText("Unknown Album"); artist.setText("Unknown Artist");
//        genre.setText("Unknown Genre"); }



    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    void createSpinner() {
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);


        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, mp3s);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
    }
    public Drawable getDrawable(String name) {
        Context context = getApplicationContext();
        int resourceId = context.getResources().getIdentifier(name, "raw", context.getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     String   mp3Name = spinner.getSelectedItem().toString();

     Uri uri=Uri.parse("R.drawable."+mp3Name);

       //  mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this,uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
           //mediaPlayer.setDataSource(getApplicationContext(),uri);
//           seekBar = findViewById(R.id.seekBar);
//           seekBar.setMax(mediaPlayer.getDuration());
//           seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//               @Override
//               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                   if (fromUser) {
//                       mediaPlayer.seekTo(progress);
//                   }
//               }
//
//               @Override
//               public void onStartTrackingTouch(SeekBar seekBar) {
//
//               }
//
//               @Override
//               public void onStopTrackingTouch(SeekBar seekBar) {
//
//               }
//           });
//
//           new Timer().scheduleAtFixedRate(new TimerTask() {
//               @Override
//               public void run() {
//                   seekBar.setProgress(mediaPlayer.getCurrentPosition());
//               }
//           }, 0, 1000);


        //   mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse("@raw/"+mp3Name));


    }
    public void previous(View view) {
        seekBar.setProgress(0);
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
        playPauseIcon.setImageResource(R.drawable.ic_play_arrow_orange_24dp);
    }

    public void next(View view) {
        seekBar.setProgress(mediaPlayer.getDuration());
        mediaPlayer.seekTo(mediaPlayer.getDuration());
        mediaPlayer.pause();
        playPauseIcon.setImageResource(R.drawable.ic_play_arrow_orange_24dp);
    }

    public void play(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playPauseIcon.setImageResource(R.drawable.ic_play_arrow_orange_24dp);
        } else {
            mediaPlayer.start();
            playPauseIcon.setImageResource(R.drawable.ic_pause_orange_24dp);
        }
    }

}