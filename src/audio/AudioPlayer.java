package audio;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

    // Musics and sounds (Will correspond to their containing list index)
    public static final int MAIN_MUSIC = 0; // For instance, this element will be the corresponding index of that music in the String[] songNames
    public static final int STAGE1_MUSIC = 1;
    public static final int STAGE2_MUSIC = 2;
    public static final int STAGE3_MUSIC = 3;

    public static final int WEAK_DEATH_SFX = 0;
    public static final int FAST_DEATH_SFX = 1;
    public static final int TANK_DEATH_SFX = 2;
    public static final int POLYVALENT_DEATH_SFX = 3;
    public static final int WEAK_HURT_SFX = 4;
    public static final int FAST_HURT_SFX = 5;
    public static final int TANK_HURT_SFX = 6;
    public static final int POLYVALENT_HURT_SFX = 7;


    private Clip[] songs, effects;
    private int currentSongId;
    private float volume = 0.7f;
    private boolean songMute, effectMute;
    private Random rand = new Random();

    public AudioPlayer() {
        loadSongs();
        loadEffects();
    }

    /** Loads every songs of the game */
    private void loadSongs() {
        // songNames will contain every music
        String[] songNames = {"ES_Closed-Book_Dream_Cave.wav", "ES_Confidentiality-Dream-Cave.wav",
                "ES_Dollhouse_Staircase_Mike_Franklyn.wav", "ES_First-on-the-Scene-Dream-Cave.wav"};
        songs =  new Clip[songNames.length];
        for(int i = 0; i< songNames.length; i++) {
            songs[i] = getClip(songNames[i]);
        }
    }

    /** Loads every SFX of the game */
    private void loadEffects() {
        // effectNames will contain every sound effects
        String[] effectNames = {"weakDeath.wav", "fastDeath.wav", "tankDeath.wav", "polyvalentDeath.wav",
                "weakHurt.wav", "fastHurt.wav", "tankHurt.wav", "polyvalentHurt.wav"}; //
        effects =  new Clip[effectNames.length];
        for(int i = 0; i< effects.length; i++) {
            effects[i] = getClip(effectNames[i]);
        }
        updateEffectsVolume();
    }

    /** Fetchs the audio files in the assets folder and converts it into a Clip object */
    private Clip getClip(String name) {



        try {
            System.out.println("Trying to get clip from assets/audio/" + name);
            File f = new File("assets/audio/" + name);
            AudioInputStream audio;
            audio = AudioSystem.getAudioInputStream(f);
            Clip c = AudioSystem.getClip();
            c.open(audio);

            return c;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            e.printStackTrace();
        }

        return null;
    }

    public void setVolume(float v) {
        this.volume = v;
        updateSongVolume();
        updateEffectsVolume();
    }

    public void stopSong() {
        if(songs[currentSongId].isActive()) {
            songs[currentSongId].stop();
        }
    }


    /** Used to play a sfx */
    public void playEffect(int effect) {
        effects[effect].stop();
        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }


    /** Used to play a song */
    public void playSong(int song) {
        stopSong();

        currentSongId = song;
        updateSongVolume();

        songs[currentSongId].setMicrosecondPosition(0);
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void muteAllSounds() {
        // Pour tout mute
        this.songMute = !songMute;
        this.effectMute = !effectMute;
        for(Clip c: songs) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }

        for(Clip c: effects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }

        if(!effectMute) {
            playEffect(WEAK_DEATH_SFX);
        }
    }

    private void updateSongVolume() {
        FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum();
        gainControl.setValue(gain);
    }

    private void updateEffectsVolume() {
        for(Clip c: effects) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}