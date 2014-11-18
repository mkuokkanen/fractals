package model;

/**
 * Common interface for model classes.
 */
public interface FractalModel<T extends FractalConfig> {

    /**
     * Set fractal configuration.
     */
    void setFractalConfig(T configuration);

    /**
     * Get fractal configuration.
     */
    T getFractalConfig();

    /**
     * Set info of drawing area size configuration.
     */
    void setScreenConfig(ScreenConfig configuration);

    /**
     * Get info of drawing area size configuration.
     */
    ScreenConfig getScreenConfig();

}
