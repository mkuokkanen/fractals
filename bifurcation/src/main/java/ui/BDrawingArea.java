package ui;

import com.google.common.base.Stopwatch;
import model.BifurcationModel;
import model.ChangeType;
import model.ScreenConfig;
import model.ScreenPixel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Subclasses JPanel to create canvas which can be used to draw images.
 *
 * @author Mikko Kuokkanen
 */
public class BDrawingArea extends JPanel {

    private static final Logger LOGGER = LoggerFactory.getLogger(BDrawingArea.class);

    private final BifurcationModel m;

    /**
     * Create drawing area with given size
     *
     * @param model
     */
    public BDrawingArea(BifurcationModel model) {
        super(true);

        LOGGER.info("Creating drawing area");

        m = checkNotNull(model);

        Dimension d = new Dimension(model.getScreenConfig().width(), model.getScreenConfig().height());
        this.setSize(d);
        this.setPreferredSize(d);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                LOGGER.info("Got window resized event: {}, height: {}",
                        e.getComponent().getWidth(), e.getComponent().getHeight());
                m.setScreenConfig(new ScreenConfig(e.getComponent().getWidth(), e.getComponent().getHeight()));
            }
        });

        model.addObserver((o, type) -> {
            if (type == ChangeType.DATA) {
                LOGGER.info("Reacting to change in model. Changed {}. Requesting repaint.", type);
                BDrawingArea.this.repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        clearBackground(g);
        drawToScreen(g);
    }

    private void clearBackground(Graphics g) {
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
    }

    private void drawToScreen(Graphics g) {
        // start timer
        Stopwatch stopwatch = Stopwatch.createStarted();

        // color
        g.setColor(new Color(0f, 0f, 0f, 0.2f));

        for (ScreenPixel each : m.knownScreenPixels()) {
            g.drawLine(each.x, each.y, each.x, each.y);
            LOGGER.debug("got {}", each);
        }

        LOGGER.info("Drew data to screen in {}", stopwatch);
    }

}
