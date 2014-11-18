package model;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Main entry point for Bifurcation calculations and data.
 *
 * @author Mikko Kuokkanen
 */
public class BifurcationModel extends AbstractFractalModel<BifurcationConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BifurcationModel.class);

    private BifurcationCalculator calculator;

    /**
     * @param screenConfig
     */
    public BifurcationModel(ScreenConfig screenConfig) {
        super(screenConfig, new BifurcationConfiguration());

        calculate();
        LOGGER.info("Created model {}", this);
    }

    public ImmutableList<ScreenPixel> knownScreenPixels() {
        List<ScreenPixel> result = new ArrayList<>();

        for (int screenX = 0; screenX < screenConfig.width(); screenX++) {
            List<Double> values = calculator.valuesForStep(screenX);
            for (Double each : values) {
                int screenY = ScaleConversion.yFromFractalToScreen(
                        getFractalConfig().minY,
                        getFractalConfig().maxY,
                        getScreenConfig().height(),
                        each);

                result.add(new ScreenPixel(screenX, screenY));
            }
        }
        return ImmutableList.copyOf(result);
    }

    public void calculate() {
        calculator = new BifurcationCalculator(screenConfig.width(), fractalConfig);

        setChanged();
        notifyObservers(ChangeType.DATA);
    }

}
