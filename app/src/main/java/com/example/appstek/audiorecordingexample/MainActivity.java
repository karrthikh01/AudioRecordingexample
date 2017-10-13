package com.example.appstek.audiorecordingexample;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,MediaPlayer.OnPreparedListener {

    boolean isStartRecording = false;
    boolean isPlayRecording = false;

    MediaPlayer mediaPlayer;
    MediaRecorder recorder;
    File audiofile = null;
    Button takeAudio, playAudio;
    String audiopath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takeAudio = (Button) findViewById(R.id.takeAudio);
        playAudio = (Button) findViewById(R.id.playAudio);
        takeAudio.setOnClickListener(this);
        playAudio.setOnClickListener(this);
    }
    @Override
    public void onPrepared(MediaPlayer mp) {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takeAudio: {
                try {
                    if (!isStartRecording) {
                        startRecording();
                        takeAudio.setBackgroundResource(R.drawable.nonrecording);
                        isStartRecording = !isStartRecording;
                    } else {
                        stopRecording();
                        takeAudio.setBackgroundResource(R.drawable.recording);
                        isStartRecording = !isStartRecording;
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            }
            case R.id.playAudio: {
                try {

                    if (!isPlayRecording) {
                        playRecording();
                        playAudio.setBackgroundResource(R.drawable.pause);
                        isPlayRecording = !isPlayRecording;
                    }
                    else {
                        stopPlaying();
                        playAudio.setBackgroundResource(R.drawable.play_enable);
                        isPlayRecording = !isPlayRecording;
                    }

                }
                catch (Exception e) {
                    System.out.println(e);
                }
                break;
            }

        }
    }
    private void stopPlaying() {
    }
    public void startRecording() throws IOException {
        File sampleDir = Environment.getExternalStorageDirectory();
        try {
            audiofile = File.createTempFile("sound", ".mp3", sampleDir);
            audiopath = audiofile.getAbsolutePath();
            System.out.println("path");
        } catch (IOException e) {

            return;
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audiofile.getAbsolutePath());
        recorder.prepare();
        recorder.start();
    }

    public void stopRecording() {

        // play.setEnabled(true);
        recorder.stop();
        playAudio.setBackgroundResource(R.drawable.play_enable);
        playAudio.setEnabled(true);
        recorder.release();
        // addRecordingToMediaLibrary();
    }

    public void playRecording() throws IOException {

        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(audiopath);
            mp.prepare();

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer player) {
                    player.stop();
                    playAudio.setBackgroundResource(R.drawable.play_enable);

                }

            });
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        } catch (IllegalStateException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        try {
            mp.setOnPreparedListener(this);
            mp.prepareAsync();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    }

