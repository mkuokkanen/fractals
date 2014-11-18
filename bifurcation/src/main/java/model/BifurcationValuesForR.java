package model;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Calculates and stores x values for given r.
 *
 * @author Mikko Kuokkanen
 */
class BifurcationValuesForR {

    private static final Logger LOGGER = LoggerFactory.getLogger(BifurcationValuesForR.class);

    private static final double INITIAL_X = 0.5;

    private final double r;
    private final int iterations;
    private final int skipIterations;
    private final ImmutableList<Double> values;

    /**
     * @param r              value for which x values will be calculated
     * @param iterations     how many values we want to find
     * @param skipIterations how many values to ignore from beginning
     */
    public BifurcationValuesForR(final double r, final int iterations, final int skipIterations) {
        LOGGER.debug("Calculating bifurcation values for r {}, iterations {}", r, iterations);

        this.r = r;
        this.iterations = iterations;
        this.skipIterations = skipIterations;
        this.values = calculate();
    }

    /**
     * @return immutable list of values for given r
     */
    private ImmutableList<Double> calculate() {
        ArrayList<Double> result = new ArrayList<>(this.iterations);
        double xn = INITIAL_X;

        // calculate values to be skipped
        for (int i = 0; i < skipIterations; i++) {
            xn = calcNextValue(xn);
        }

        // calculate values to stored
        for (int i = 0; i < iterations; i++) {
            xn = calcNextValue(xn);
            result.add(xn);
        }

        return ImmutableList.copyOf(result);
    }

    /**
     * Calculates new x value
     *
     * @param xn previous x value
     * @return new x value
     */
    private double calcNextValue(double xn) {
        return r * xn * (1 - xn);
    }

    /**
     * @return r constant
     */
    public double getR() {
        return r;
    }

    /**
     * @return Immutable List of values for given r.
     */
    public ImmutableList<Double> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("r", r)
                .add("iter", iterations)
                .add("vals", values)
                .toString();
    }
}
