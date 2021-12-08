package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
TextView tv_title,tv_start, tv_end;
ImageButton ib_play,ib_forward,ib_previous,ib_stop;
SeekBar sb_time;
ImageView iv_disc;
Animation animation;
ArrayList<Song> arraySong;
int position=0;//thứ tự của bài hát
int x=0; //xét điều kiện cho animation trong nút start
MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addSong();
        //khởi tạo animation
        animation= AnimationUtils.loadAnimation(this,R.anim.anima);

        createSong();
        //nút start
        ib_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//nếu đang chạy bài hát, bấm thì sẽ pause và đổi src thành hình pause
                if(mediaPlayer.isPlaying()){
                    ib_play.setImageResource(R.drawable.play2);
                    mediaPlayer.pause();
                }else{
                    ib_play.setImageResource(R.drawable.pause2);
                    mediaPlayer.start();
                }
                setTimeTotal();
                setTimeSong();
                if(x==0){
                    //start animation
                    iv_disc.startAnimation(animation);
                    x=1;
                }else{
                    iv_disc.clearAnimation();
                    x=0;
                }

            }
        });

        ib_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                ib_play.setImageResource(R.drawable.play2);
                createSong();
                tv_end.setText("00:00");
                iv_disc.clearAnimation();
            }
        });

        ib_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ib_play.setImageResource(R.drawable.pause2);
                position++;
                if(position>arraySong.size()-1){
                    position=0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                createSong();
                mediaPlayer.start();
                setTimeTotal();
                setTimeSong();
            }
        });
        ib_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ib_play.setImageResource(R.drawable.pause2);
                position--;
                if(position<0){
                    position=arraySong.size()-1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                createSong();
                mediaPlayer.start();
                setTimeTotal();
                setTimeSong();
            }
        });
        sb_time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
//chỉ dùng ónStop
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(sb_time.getProgress());
            }
        });

    }


    private void setTimeSong (){
        //khởi tạo handler để điều chỉnh thời gian chạy của bài hát
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat configTime = new SimpleDateFormat("mm:ss");
                tv_start.setText(configTime.format(mediaPlayer.getCurrentPosition()));
                //update seekbar progress
                sb_time.setProgress(mediaPlayer.getCurrentPosition());
                //kiểm tra hết bài thì tự next bài khác
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                        ib_play.setImageResource(R.drawable.pause2);
                        position++;
                        if(position>arraySong.size()-1){
                            position=0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        createSong();
                        mediaPlayer.start();
                        setTimeTotal();

                    }
                });
                handler.postDelayed(this,500);
            }
        },100);
    }
    private void setTimeTotal(){
        SimpleDateFormat configTime = new SimpleDateFormat("mm:ss");
        tv_end.setText(configTime.format(mediaPlayer.getDuration()));
        //gán
        sb_time.setMax(mediaPlayer.getDuration());

    }
    private void createSong(){
        mediaPlayer= MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
        tv_title.setText(arraySong.get(position).getTitle());
    }
    private void addSong(){
        arraySong=new ArrayList<Song>();
        arraySong.add(new Song("Buớc qua nhau",R.raw.buocquanhau));
        arraySong.add(new Song("Buồn",R.raw.buon));
        arraySong.add(new Song("Easy on my",R.raw.easyonme));
        arraySong.add(new Song("Hotel Cali",R.raw.hotelcalifinia));
        arraySong.add(new Song("Last Christmas",R.raw.lastchrismax));
        arraySong.add(new Song("Trốn tìm",R.raw.trontim));
    }
    private void init(){
        ib_play=(ImageButton) findViewById(R.id.bt_play);
        ib_forward=(ImageButton) findViewById(R.id.bt_forward);
        ib_previous=(ImageButton) findViewById(R.id.bt_previous);
        ib_stop=(ImageButton) findViewById(R.id.bt_stop);
        sb_time=(SeekBar) findViewById(R.id.seekBar);
        tv_end=(TextView) findViewById(R.id.tv_end);
        tv_title=(TextView) findViewById(R.id.tv_title);
        tv_start=(TextView) findViewById(R.id.tv_start);
        iv_disc=(ImageView) findViewById(R.id.iv_disc);

    }
}