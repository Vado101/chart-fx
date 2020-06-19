package de.gsi.my.test.oscilloscope.core;

import javafx.scene.control.Alert;
import de.gsi.my.test.oscilloscope.OscilloscopeMain;

import javax.sound.sampled.LineUnavailableException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReaderModule implements Runnable {

    private final Reader reader;

    private AudioFloatConverter converter;

    private SoundCardConfig soundCardConfig;

    private final ExecutorService service;

    private volatile boolean start;

    public ReaderModule(SoundCardConfig soundCardConfig) {
        this.soundCardConfig = soundCardConfig;
        reader = new Reader();
        service = Executors.newSingleThreadExecutor();
        start = false;
    }

    public boolean start() {
        try {
            reader.captureLine();

            converter = AudioFloatConverter.getConverter(soundCardConfig.getAudioFormat());

            start = true;

            service.submit(this);

            return true;

        } catch (LineUnavailableException e) {
            System.out.println("Channel close");
        } catch (NullPointerException e) {
            System.out.println("No mixer in system");
        }

        return false;
    }

    @Override
    public void run() {
        while (start) {
            byte[] dataByte = new byte[soundCardConfig.getBufferSize()];
            float[] dataFloat = new float[dataByte.length / soundCardConfig
                    .getAudioFormat().getFrameSize()];

            int count = reader.getData(dataByte, dataByte.length);
            converter.toFloatArray(dataByte, dataFloat);
            OscilloscopeMain.setAxesValues(dataFloat);
        }

        reader.closeLine();
    }

    public void stop() {
        start = false;
    }

    public void completion() {
        service.shutdown();
    }

    public Reader getReader() {
        return reader;
    }

    public boolean isStop() {
        return start;
    }
}
