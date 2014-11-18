package ui;

import layout.SpringUtilities;
import model.ChangeType;
import model.MandelbrotConfig;
import model.MandelbrotModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Subclasses JPanel to create area with settings ui components.
 *
 * @author Mikko Kuokkanen
 */
public class MSettingsArea extends JPanel implements ActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MSettingsArea.class);

    private static final String[] LABELS = {"Width: ", "Height: ", "Top: ", "Right: ", "Bottom: ", "Left: ", "Iterations: "};
    private static final int ENTRIES = LABELS.length;

    private final MandelbrotModel m;

    private final JTextField textFieldWidth = new JTextField(10);
    private final JTextField textFieldHeight = new JTextField(10);
    private final JTextField textFieldTop = new JTextField(10);
    private final JTextField textFieldRight = new JTextField(10);
    private final JTextField textFieldBottom = new JTextField(10);
    private final JTextField textFieldLeft = new JTextField(10);
    private final JTextField textFieldIterations = new JTextField(10);


    public MSettingsArea(MandelbrotModel mandelbrotModel) {
        super(new SpringLayout());

        this.m = checkNotNull(mandelbrotModel);

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

        LOGGER.info("Created {} with size w:{}, h:{}",
                getClass().getSimpleName(), getWidth(), getHeight());
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
                m.setFractalConfig(new MandelbrotConfig());
                modelToFields(m);
                break;
            case "redraw":
                MandelbrotConfig c = modelFromFields();
                m.setFractalConfig(c);
                break;
            default:
                LOGGER.error("Unknown command {}", e.getActionCommand());
                break;
        }

    }

    private void modelToFields(MandelbrotModel m) {
        textFieldWidth.setText(String.valueOf(m.getScreenConfig().width()));
        textFieldHeight.setText(String.valueOf(m.getScreenConfig().height()));

        textFieldTop.setText(String.valueOf(m.getFractalConfig().maxY));
        textFieldBottom.setText(String.valueOf(m.getFractalConfig().minY));
        textFieldLeft.setText(String.valueOf(m.getFractalConfig().minX));
        textFieldRight.setText(String.valueOf(m.getFractalConfig().maxX));
        textFieldIterations.setText(String.valueOf(m.getFractalConfig().iterations));
    }

    private MandelbrotConfig modelFromFields() {
        return new MandelbrotConfig(
                Double.valueOf(textFieldLeft.getText()),
                Double.valueOf(textFieldRight.getText()),
                Double.valueOf(textFieldBottom.getText()),
                Double.valueOf(textFieldTop.getText()),
                Integer.valueOf(textFieldIterations.getText())
        );
    }
}
