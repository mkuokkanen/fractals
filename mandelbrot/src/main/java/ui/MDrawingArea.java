package ui;

import com.google.common.base.Stopwatch;
import model.ChangeType;
import model.MandelbrotModel;
import model.ScreenConfig;
import model.ScreenPixel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Mandelbrot Drawing area
 * <p/>
 * Created 17.6.2005.
 *
 * @author Mikko Kuokkanen
 */
public class MDrawingArea extends JPanel implements MouseListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MDrawingArea.class);

    private MandelbrotModel m;

    public MDrawingArea(MandelbrotModel model) {
        super(true);

        this.m = checkNotNull(model);

        Dimension d = new Dimension(m.getScreenConfig().width(), m.getScreenConfig().height());
        this.setSize(d);
        this.setPreferredSize(d);

        this.addMouseListener(this);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                LOGGER.info("Got window resized event: {}, height: {}",
                        e.getComponent().getWidth(), e.getComponent().getHeight());
                m.setScreenConfig(new ScreenConfig(e.getComponent().getWidth(), e.getComponent().getHeight()));
            }
        });

        this.m.addObserver((o, type) -> {
            if (type == ChangeType.DATA) {
                LOGGER.info("Reacting to change in model. Changed {}. Requesting repaint.", type);
                MDrawingArea.this.repaint();
            }
        });

        LOGGER.info("Created {} with size w:{}, h:{}",
                getClass().getSimpleName(), getWidth(), getHeight());
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
        Stopwatch stopwatch = Stopwatch.createStarted();

        for (ScreenPixel each : m.data()) {
            g.setColor(m.getColorForPoint(each.mp));
            g.drawLine(each.x, each.y, each.x, each.y);
            LOGGER.debug("got {}", each);
        }

        LOGGER.info("Drew data to screen in {}", stopwatch);
    }

    public void mouseClicked(MouseEvent e) {
        m.zoomTowardsPoint(e.getX(), e.getY());
        this.repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }


}
