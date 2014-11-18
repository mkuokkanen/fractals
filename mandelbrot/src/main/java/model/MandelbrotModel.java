package model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Mandelbrot Model
 * <p/>
 * Created 17.6.2005.
 *
 * @author Mikko Kuokkanen
 */
public class MandelbrotModel extends AbstractFractalModel<MandelbrotConfig> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MandelbrotModel.class);

    private final int DEFAULT_ZOOM_AMOUNT = 2;

    private List<ScreenPixel> data;

    /**
     * @param screenConfig
     * @param fractalConfig
     */
    public MandelbrotModel(ScreenConfig screenConfig, MandelbrotConfig fractalConfig) {
        super(screenConfig, fractalConfig);
        this.data = Collections.emptyList();

        calculate();
        LOGGER.info("Created model {}", this);
    }

    public List<ScreenPixel> data() {
        return Collections.unmodifiableList(data);
    }

    @Override
    protected void calculate() {
        Stopwatch stopwatch = Stopwatch.createStarted();

        List<ScreenPixel> resultData = new ArrayList<>();

        for (int screenX = 0; screenX < screenConfig.width(); screenX++) {
            for (int screenY = 0; screenY < screenConfig.height(); screenY++) {
                double fractalX = ScaleConversion.xFromScreenToFractal(
                        fractalConfig.minX, fractalConfig.maxX, screenConfig.width(), screenX);
                double fractalY = ScaleConversion.yFromScreenToFractal(
                        fractalConfig.minY, fractalConfig.maxY, screenConfig.height(), screenY);

                MandelbrotPoint mp = new MandelbrotPoint(fractalX, fractalY, fractalConfig.iterations);

                resultData.add(new ScreenPixel(screenX, screenY, mp));
            }
        }

        this.data = resultData;

        LOGGER.info("Recalculated data in {}", stopwatch);

        setChanged();
        notifyObservers(ChangeType.DATA);
    }


    /**
     * Shrinks the boundaries of feigenbaum area towards given point in screen.
     */
    public void zoomTowardsPoint(int x, int y) {
        double xx = ScaleConversion.xFromScreenToFractal(
                fractalConfig.minX, fractalConfig.maxX, screenConfig.width(), x);
        double yy = ScaleConversion.yFromScreenToFractal(
                fractalConfig.minY, fractalConfig.maxY, screenConfig.height(), y);

        double xMin2 = fractalConfig.minX + ((xx - fractalConfig.minX) / DEFAULT_ZOOM_AMOUNT);
        double xMax2 = fractalConfig.maxX - ((fractalConfig.maxX - xx) / DEFAULT_ZOOM_AMOUNT);

        double yMin2 = fractalConfig.minY + ((yy - fractalConfig.minY) / DEFAULT_ZOOM_AMOUNT);
        double yMax2 = fractalConfig.maxY - ((fractalConfig.maxY - yy) / DEFAULT_ZOOM_AMOUNT);

        MandelbrotConfig newConfig = new MandelbrotConfig(xMin2, xMax2, yMin2, yMax2,
                fractalConfig.iterations);

        setFractalConfig(newConfig);
    }


    public Color getColorForPoint(MandelbrotPoint mp) {
        Color c;
        if (mp.isBounded()) {
            c = Color.black;
        } else {
            //c = Color.white;
            float grayVal = 1.0f / fractalConfig.iterations * mp.getEscapeIteration();
            c = new Color(grayVal, grayVal, grayVal);
        }
        return c;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("screen", screenConfig)
                .add("fractal", fractalConfig)
                .add("data elements", data.size())
                .toString();
    }

}
