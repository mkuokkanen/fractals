package app;

import model.BifurcationModel;
import model.ScreenConfig;
import ui.BSettingsArea;
import ui.BDrawingArea;
import ui.MainWindow;

import javax.swing.*;

/**
 * Application entry point.
 *
 * @author Mikko Kuokkanen
 */
public class BifurcationApplication {

    static final int DEFAULT_WIDTH = 640;
    static final int DEFAULT_HEIGHT = 400;
    static final String TITLE = "Bifurcation proto";

    /**
     * Constructor
     */
    public BifurcationApplication() {
        BifurcationModel model = new BifurcationModel(new ScreenConfig(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        JPanel drawingArea = new BDrawingArea(model);
        JPanel settingsPanel = new BSettingsArea(model);
        MainWindow window = new MainWindow(TITLE, model, drawingArea, settingsPanel);
        window.setVisible(true);
    }

    /**
     * Application entry point.
     * @param args
     */
    public static void main(String... args) {
        BifurcationApplication application = new BifurcationApplication();
    }
}
