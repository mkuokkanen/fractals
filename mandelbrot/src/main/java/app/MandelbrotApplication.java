package app;

import model.MandelbrotConfig;
import model.MandelbrotModel;
import model.ScreenConfig;
import ui.MainWindow;
import ui.MDrawingArea;
import ui.MSettingsArea;

import javax.swing.*;

/**
 * Application entry point.
 *
 * @author Mikko Kuokkanen
 */
public class MandelbrotApplication {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 600;
    public static final String TITLE = "MandelBrot proto";

    /**
     * Constructor.
     */
    public MandelbrotApplication() {
        MandelbrotModel model = new MandelbrotModel(
                new ScreenConfig(DEFAULT_WIDTH, DEFAULT_HEIGHT),
                new MandelbrotConfig());

        JPanel drawingArea = new MDrawingArea(model);
        JPanel settingsPanel = new MSettingsArea(model);

        MainWindow window = new MainWindow(TITLE, model, drawingArea, settingsPanel);
        window.setVisible(true);
    }

    /**
     * Application entry point.
     *
     * @param args
     */
    public static void main(String... args) {
        MandelbrotApplication application = new MandelbrotApplication();
    }
}
