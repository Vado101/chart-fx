package de.gsi.my.test.oscilloscope.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import de.gsi.my.test.oscilloscope.core.CoreFacade;

public class MainWindowOscilloscopeController {

    private static boolean flagStart = false;

    @FXML
    private Button startButton;

    @FXML
    private void startOscilloscope() {
        Platform.runLater(() -> {
            if (!flagStart) {
                CoreFacade.start();
                if (CoreFacade.isRun()) {
                    startButton.setText("STOP");
                    flagStart = true;
                }
            } else {
                CoreFacade.stop();
                if (!CoreFacade.isRun()) {
                    startButton.setText("START");
                    flagStart = false;
                }
            }
        });
    }
}
