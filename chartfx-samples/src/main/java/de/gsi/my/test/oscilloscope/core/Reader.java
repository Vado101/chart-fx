package de.gsi.my.test.oscilloscope.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.util.ArrayList;

public class Reader {
    private static final Logger LOGGER = LoggerFactory.getLogger(Reader.class);

    private final String DEFAULT_MIXER_DESCRIPTION = "DirectSound Capture";

    private Mixer mixerAudioCapture;

    private SoundCardConfig soundCardConfig;


    private TargetDataLine line = null;

    Reader() {
        setDefaultMixer();
    }

    private void setDefaultMixer() {
        soundCardConfig = new SoundCardConfig();
        ArrayList<Mixer.Info> mixerList = soundCardConfig.getMixerListForAudioCapture();

        if (!mixerList.isEmpty()) {
            for (Mixer.Info mixer : mixerList) {
                if (mixer.getDescription().contains(DEFAULT_MIXER_DESCRIPTION)) {
                    mixerAudioCapture = AudioSystem.getMixer(mixer);
                    break;
                }
            }
        } else {
            mixerAudioCapture = null;
        }
    }


    void captureLine() throws LineUnavailableException {
        if (line != null) {
            line.close();
            line = null;
            LOGGER.info("Channel close");
        }

        DataLine.Info dataLineTarget = new DataLine.Info(TargetDataLine.class, soundCardConfig.getAudioFormat());
        line = (TargetDataLine) mixerAudioCapture.getLine(dataLineTarget);
        line.open(soundCardConfig.getAudioFormat());
        line.start();

        LOGGER.info("Open mixer: {}", mixerAudioCapture.getMixerInfo().getName());
    }

    int getData(byte[] data, int length) {
        return line.read(data, 0, length);
    }


    void closeLine() {
        if (line != null) {
            line.close();
            line = null;
            LOGGER.info("Channel close");
        }
    }
}
