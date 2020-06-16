package de.gsi.my.test.oscilloscope.core;

import javax.sound.sampled.*;
import java.util.ArrayList;

public final class SoundCardConfig {

    private float sampleRate;

    private enum SampleRateValue {
        FREQUENCY_40960(40960.0f),
        FREQUENCY_44100(44100.0f),
        FREQUENCY_48000(48000.0f),
        FREQUENCY_88200(88200.0f),
        FREQUENCY_96000(96000.0f),
        FREQUENCY_176400(176400.0f),
        FREQUENCY_192000(192000.0f);

        private float value;

        SampleRateValue(float value) {
            this.value = value;
        }

        public float getValue() {
            return value;
        }
    }

    private static ArrayList<Float> sampleRateList = new ArrayList<>();

    static {
        for (SampleRateValue sampleRateValue : SampleRateValue.values()) {
            sampleRateList.add(sampleRateValue.getValue());
        }
    }

    public static ArrayList<Float> getSampleRateList() {
        return sampleRateList;
    }

    public float getSampleRate()
    {
        return sampleRate;
    }

    public void setSampleRate(float sampleRate) {
        if (sampleRateList.contains(sampleRate)) {
            this.sampleRate = sampleRate;
            setUpdate(true);
        }
    }

    private int sampleSizeInBits;

    private enum SampleSizeInBitsValue {
        BIT_8(8),
        BIT_16(16),
        BIT_24(24);

        private int value;

        SampleSizeInBitsValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static ArrayList<Integer> sampleSizeInBitsList = new ArrayList<>();

    static {
        for (SampleSizeInBitsValue sampleSizeInBitsValue : SampleSizeInBitsValue.values()) {
            sampleSizeInBitsList.add(sampleSizeInBitsValue.getValue());
        }
    }

    public static ArrayList<Integer> getSampleSizeInBitsList() {
        return sampleSizeInBitsList;
    }

    public int getSampleSizeInBits(){
        return sampleSizeInBits;
    }

    public void setSampleSizeInBits(int sampleSizeInBits) {
        if (sampleSizeInBitsList.contains(sampleSizeInBits)) {
            this.sampleSizeInBits = sampleSizeInBits;
            setUpdate(true);
        }
    }

    private int channels;

    public enum ChannelValue {
        MONO("mono", 1),
        STEREO("steteo", 2);

        private String name;
        private int value;

        ChannelValue(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }

    private static ArrayList<Integer> channelList = new ArrayList<>();

    static {
        for (ChannelValue channelValue : ChannelValue.values()) {
            channelList.add(channelValue.getValue());
        }
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        if (channelList.contains(channels)) {
            this.channels = channels;
            setUpdate(true);
        }
    }

    private int bufferSize;

    private enum BufferSizeValue {
        BUFFER_SIZE_1024(1024),
        BUFFER_SIZE_2048(2048),
        BUFFER_SIZE_4096(4096),
        BUFFER_SIZE_8192(8192),
        BUFFER_SIZE_16384(16384),
        BUFFER_SIZE_32768(32768);

        private int value;

        BufferSizeValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static ArrayList<Integer> bufferSizeList = new ArrayList<>();

    static {
        for (BufferSizeValue bufferSizeValue : BufferSizeValue.values()) {
            bufferSizeList.add(bufferSizeValue.getValue());
        }
    }

    public static ArrayList<Integer> getBufferSizeList() {
        return bufferSizeList;
    }

    public void setBufferSize(int bufferSize) {
        if (bufferSizeList.contains(bufferSize)) {
            this.bufferSize = bufferSize;
            setUpdate(true);
        }
    }

    public int getBufferSize() {
        return bufferSize;
    }

    private transient boolean signed;

    public boolean isSigned() {
        return signed;
    }

    private transient boolean bigEndian;

    public boolean isBigEndian() {
        return bigEndian;
    }

    private transient AudioFormat audioFormat;

    private void setAudioFormat() {
        audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    private transient boolean update;

    public boolean isUpdate() {
        return update;
    }

    private void setUpdate(boolean update) {
        this.update = update;
    }

    public SoundCardConfig() {
        setDefaultConfig();
        setAudioFormat();
    }

    public void updateConfigure() {
        if (isUpdate()) {
            setAudioFormat();
            setUpdate(false);
        }
    }

    public ArrayList<Mixer.Info> getMixerListForAudioCapture() {
        Mixer.Info[] mixerInfoList = AudioSystem.getMixerInfo();
        ArrayList<Mixer.Info> mixerList = new ArrayList<>();

        for (Mixer.Info mixerInfo: mixerInfoList) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);

            DataLine.Info dataLine = new DataLine.Info(TargetDataLine.class, audioFormat);

            if (mixer.isLineSupported(dataLine)) {
                mixerList.add(mixerInfo);
            }
        }

        return mixerList;
    }

    public void setDefaultConfig() {
        this.sampleRate = SampleRateValue.FREQUENCY_44100.getValue();
        this.sampleSizeInBits = SampleSizeInBitsValue.BIT_16.getValue();
        this.channels = ChannelValue.MONO.getValue();
        this.bufferSize = BufferSizeValue.BUFFER_SIZE_4096.getValue();
        this.signed = true;
        this.bigEndian = false;
        setUpdate(true);
    }
}
