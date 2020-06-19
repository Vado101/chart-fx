package de.gsi.my.test.oscilloscope;

import de.gsi.chart.XYChart;
import de.gsi.chart.axes.spi.DefaultNumericAxis;
import de.gsi.chart.plugins.Zoomer;
import de.gsi.chart.renderer.spi.ReducingLineRenderer;
import de.gsi.dataset.spi.FloatDataSet;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.gsi.my.test.oscilloscope.core.CoreFacade;

import java.io.IOException;

public class OscilloscopeMain extends Application {

    private static FloatDataSet dataSet;

    public static FloatDataSet getDataSet() {
        return dataSet;
    }

    @Override
    public void init() {

    }

    @Override
    public void start(Stage stage) {
        Pane root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindowOscilloscope.fxml"));

        try {
            root = loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        createOscilloscope((AnchorPane) root);


        Scene scene = new Scene(root);
        stage.setMinWidth(900);
        stage.setMinHeight(500);
        stage.setTitle("Oscilloscope");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(evt -> Platform.exit());
    }

    @Override
    public void stop() {
        CoreFacade.completion();
    }

    public static void main(String[] args) {
        launch(args);
    }


    private void createOscilloscope(AnchorPane pane) {
        final DefaultNumericAxis xAxis = new DefaultNumericAxis("time");
        xAxis.setAnimated(false);
        final DefaultNumericAxis yAxis = new DefaultNumericAxis("Amplitude");
        yAxis.setAnimated(false);

        XYChart chart = new XYChart(xAxis, yAxis);
        chart.setPrefHeight(450.0);
        chart.setPrefWidth(730);

        pane.getChildren().add(chart);
        AnchorPane.setTopAnchor(chart, 0.0);
        AnchorPane.setLeftAnchor(chart, 0.0);
        AnchorPane.setBottomAnchor(chart, 0.0);

        chart.getYAxis().setAutoRanging(false);
        chart.getYAxis().setMin(-1.1);
        chart.getYAxis().setMax(1.1);

        ReducingLineRenderer renderer = new ReducingLineRenderer(4096);
        chart.getRenderers().removeAll();
        chart.getRenderers().add(renderer);

        dataSet = new FloatDataSet("Signal data");
        renderer.getDatasets().add(dataSet);

        Zoomer zoomer = new Zoomer();
        chart.getPlugins().add(zoomer);
    }

    public static void setAxesValues(float[] signalData) {
        float[] xValues = new float[signalData.length];
        float[] yValues = new float[signalData.length];

        float sampleRate = 192000.0f;

        for (int i = 0; i < signalData.length; ++i) {
            xValues[i] = ((float) i) / sampleRate;
            yValues[i] = signalData[i];
        }

        dataSet.set(xValues, yValues);
    }
}
