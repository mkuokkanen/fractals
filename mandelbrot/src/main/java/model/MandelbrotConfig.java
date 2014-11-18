package model;

import com.google.common.base.MoreObjects;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Fractal plane.
 * X and Y are defined in conventional way, X being horizontal axis and Y vertical axis.
 * No need to create getter methods for this immutable class.
 * Lets see how stuff works with public final fields.
 *
 * @author Mikko Kuokkanen
 */
public class MandelbrotConfig implements FractalConfig {

    private final static double DEFAULT_MIN_X = -2.1;
    private final static double DEFAULT_MAX_X = 1.1;
    private final static double DEFAULT_MIN_Y = -1.6;
    private final static double DEFAULT_MAX_Y = 1.6;

    private final static int DEFAULT_ITERATION = 50;

    public final int iterations;

    public final double minX;
    public final double maxX;
    public final double minY;
    public final double maxY;

    public final double width;
    public final double height;

    /**
     * Plane constructor with give values.
     *
     * @param minX       left limit of the area (minimum a or r in some bifurcation equations)
     * @param maxX       right limit of the area (maximum a or r in some bifurcation equations)
     * @param minY       bottom limit of the area (minimum x in some bifurcation equations)
     * @param maxY       top limit of the area (maximum x in some bifurcation equations)
     * @param iterations how many values to iterate per r
     */
    public MandelbrotConfig(
            final double minX, final double maxX, final double minY, final double maxY,
            final int iterations) {
        checkArgument(maxX > minX, "max x (%s) must be bigger than min x (%s)", maxX, minX);
        checkArgument(maxY > minY, "max y (%s) must be bigger than min y (%s)", maxY, minY);
        checkArgument(iterations > 0, "Iterations must be > 0, was %s", iterations);

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;

        this.iterations = iterations;

        this.width = maxX - minX;
        this.height = maxY - minY;
    }

    /**
     * Plane constructor with default values.
     */
    public MandelbrotConfig() {
        this(DEFAULT_MIN_X, DEFAULT_MAX_X, DEFAULT_MIN_Y, DEFAULT_MAX_Y,
                DEFAULT_ITERATION);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("minX", minX)
                .add("maxX", maxX)
                .add("minY", minY)
                .add("maxY", maxY)
                .add("iterations", iterations)
                .toString();
    }
}
