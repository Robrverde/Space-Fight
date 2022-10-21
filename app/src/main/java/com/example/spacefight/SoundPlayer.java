package com.example.spacefight;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {

    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX = 2;
    private static SoundPool soundPool;
    private static int projectileSound;


    public SoundPlayer(Context context)
    {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }
        else
        {
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }

        projectileSound = soundPool.load(context, R.raw.projectile, 1);
    }

    public void playProjectileSound()
    {
        soundPool.play(projectileSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
