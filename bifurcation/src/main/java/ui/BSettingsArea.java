package ui;

import layout.SpringUtilities;
import model.BifurcationConfiguration;
import model.BifurcationModel;
import model.ChangeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Subclasses JPanel to create area with settings ui components.
 *
 * @author Mikko Kuokkanen
 */
public class BSettingsArea extends JPanel implements ActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BSettingsArea.class);

    private static final String[] LABELS = {"Width: ", "Height: ", "Top: ", "Right: ", "Bottom: ", "Left: ", "Iterations: ", "Skipped iters: "};
    private static final int ENTRIES = LABELS.length;


    private final BifurcationModel m;

    private final JTextField textFieldWidth = new JTextField(10);
    private final JTextField textFieldHeight = new JTextField(10);
    private final JTextField textFieldTop = new JTextField(10);
    private final JTextField textFieldRight = new JTextField(10);
    private final JTextField textFieldBottom = new JTextField(10);
    private final JTextField textFieldLeft = new JTextField(10);
    private final JTextField textFieldIterations = new JTextField(10);
    private final JTextField textFieldSkipped = new JTextField(10);


    public BSettingsArea(BifurcationModel model) {
        super(new SpringLayout());

        this.m = model;

        this.m.addObserver((o, type) -> {
            if (ChangeType.FRACTALCONFIG == type || type == ChangeType.SCREENCONFIG) {
                LOGGER.info("Reacting to change in model. Changed {}", type);
                modelToFields(m);
            }
        });

        addEntry(LABELS[0], textFieldWidth);
        addEntry(LABELS[1], textFieldHeight);
        addEntry(LABELS[2], textFieldTop);
        addEntry(LABELS[3], textFieldRight);
        addEntry(LABELS[4], textFieldBottom);
        addEntry(LABELS[5], textFieldLeft);
        addEntry(LABELS[6], textFieldIterations);
        addEntry(LABELS[7], textFieldSkipped);

        textFieldWidth.setEditable(false);
        textFieldHeight.setEditable(false);

        // Fill text fields
        modelToFields(m);

        // Reset
        JButton resetButton = new JButton("Reset");
        resetButton.setActionCommand("reset");
        resetButton.addActionListener(this);
        add(resetButton);

        // Redraw
        JButton redrawButton = new JButton("Redraw");
        redrawButton.setActionCommand("redraw");
        redrawButton.addActionListener(this);
        add(redrawButton);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(this,
                ENTRIES + 1, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        //Set up the content pane.
        setOpaque(true);  //content panes must be opaque
    }

    private void addEntry(String labelString, JTextField field) {
        JLabel labelTop = new JLabel(labelString, JLabel.TRAILING);
        add(labelTop);
        labelTop.setLabelFor(field);
        add(field);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "reset":
                m.setFractalConfig(new BifurcationConfiguration());
                modelToFields(m);
                break;
            case "redraw":
                m.setFractalConfig(modelFromFields());
                break;
            default:
                LOGGER.error("Unknown command {}", e.getActionCommand());
                break;
        }
    }

    private void modelToFields(BifurcationModel modelArg) {
        textFieldWidth.setText(String.valueOf(modelArg.getScreenConfig().width()));
        textFieldHeight.setText(String.valueOf(modelArg.getScreenConfig().height()));

        textFieldTop.setText(String.valueOf(modelArg.getFractalConfig().maxY));
        textFieldBottom.setText(String.valueOf(modelArg.getFractalConfig().minY));
        textFieldLeft.setText(String.valueOf(modelArg.getFractalConfig().minX));
        textFieldRight.setText(String.valueOf(modelArg.getFractalConfig().maxX));
        textFieldIterations.setText(String.valueOf(modelArg.getFractalConfig().iterations));
        textFieldSkipped.setText(String.valueOf(modelArg.getFractalConfig().skipped));
    }

    private BifurcationConfiguration modelFromFields() {
        return new BifurcationConfiguration(
                Double.valueOf(textFieldLeft.getText()),
                Double.valueOf(textFieldRight.getText()),
                Double.valueOf(textFieldBottom.getText()),
                Double.valueOf(textFieldTop.getText()),
                Integer.valueOf(textFieldIterations.getText()),
                Integer.valueOf(textFieldSkipped.getText())
        );
    }

}
