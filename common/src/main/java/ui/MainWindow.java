package ui;

import model.FractalModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Application Main Window.
 *
 * @author Mikko Kuokkanen
 */
public class MainWindow extends JFrame {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainWindow.class);


    /**
     * Create Main Window with given size
     *
     * @param model
     * @param drawingArea
     * @param settingsPanel
     */
    public MainWindow(String title, FractalModel model, JPanel drawingArea, JPanel settingsPanel) {
        super(title);

        LOGGER.info("Creating main window");

        checkNotNull(model);
        checkNotNull(drawingArea);
        checkNotNull(settingsPanel);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // drawing area
        setupDrawingArea(drawingArea);
        setupSettingsArea(settingsPanel);
        setupSize(model);
    }

    private void setupDrawingArea(JPanel drawingArea) {
        getContentPane().add(drawingArea, BorderLayout.CENTER);
    }

    private void setupSettingsArea(JPanel settingsArea) {
        JPanel outerSettingsPanel = new JPanel(new FlowLayout());
        outerSettingsPanel.add(settingsArea);

        getContentPane().add(outerSettingsPanel, BorderLayout.EAST);
    }

    private void setupSize(FractalModel model) {
        setSize(model.getScreenConfig().width(), model.getScreenConfig().height());
        setResizable(true);
        pack();
    }
}
