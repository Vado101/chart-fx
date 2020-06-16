package de.gsi.my.test.oscilloscope.core;

public class CoreFacade {

    private static final ReaderModule readerModule = new ReaderModule(new SoundCardConfig());

    private volatile static boolean run = false;

    private CoreFacade() {}

    public static void start() {
        if (readerModule.start()) {
            run = true;
        }
    }

    public static void stop() {
        if (run) {
            run = false;
            readerModule.stop();
        }
    }

    public static void completion() {
        stop();
        readerModule.completion();
    }

    public static Reader getReader() {
        return readerModule.getReader();
    }

    public static boolean isRun() {
        return run;
    }
}
