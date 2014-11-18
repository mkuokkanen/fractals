package model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Calculates how x behaves when r is given. Used equation is "xn+1 = r * xn * (1−xn)"
 *
 * More info can be found from e.g. here http://en.wikipedia.org/wiki/Bifurcation_diagram
 *
 * @author Mikko Kuokkanen
 */
class BifurcationCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(BifurcationCalculator.class);

    private final int steps;
    private final BifurcationConfiguration configuration;
    private final List<BifurcationValuesForR> calculatedValues;


    /**
     * Create Bifurcation with given values.
     *
     * @param steps  to how many steps to divide the plane width
     * @param config fractalConfig metadata
     */
    public BifurcationCalculator(final int steps,
                                 final BifurcationConfiguration config) {
        checkArgument(steps > 0, "Steps must be > 0, was %s", steps);

        this.steps = steps;
        this.configuration = checkNotNull(config);

        Stopwatch stopwatch = Stopwatch.createStarted();
        calculatedValues = calculate();
        LOGGER.info("Created {} in {} ", this, stopwatch);
    }

    /**
     * Go through all steps and calculate all x values for each step.
     *
     * @return calculation results.
     */
    private ImmutableList<BifurcationValuesForR> calculate() {
        List<BifurcationValuesForR> result = new ArrayList<>(steps);

        for (int i = 0; i < steps; i++) {
            double x = configuration.minX + (stepWidth() * i);
            result.add(new BifurcationValuesForR(x, configuration.iterations, configuration.skipped));
        }

        return ImmutableList.copyOf(result);
    }

    /**
     * Get values for specific step from latest calculated result set.
     *
     * @param step
     * @return
     */
    public ImmutableList<Double> valuesForStep(final int step) {
        checkArgument(step >= 0 && step < steps, "step must be > 0 and <= %s, was %s", steps, step);
        return calculatedValues.get(step).getValues();
    }

    public double stepWidth() {
        return configuration.width / steps;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fractalConfig", configuration)
                .add("steps", steps)
                .toString();
    }
}
