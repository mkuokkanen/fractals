package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Some common methods for model classes.
 *
 * @param <T> Type of configuration object
 */
abstract class AbstractFractalModel<T extends FractalConfig> extends Observable implements FractalModel<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFractalModel.class);

    protected ScreenConfig screenConfig;
    protected T fractalConfig;

    /**
     * Constructor with common elements for all FractalModels.
     *
     * @param screenConfiguration
     * @param fractalConfiguration
     */
    public AbstractFractalModel(ScreenConfig screenConfiguration, T fractalConfiguration) {
        this.screenConfig = screenConfiguration;
        this.fractalConfig = fractalConfiguration;
    }

    @Override
    public ScreenConfig getScreenConfig() {
        return screenConfig;
    }

    @Override
    public void setScreenConfig(ScreenConfig configuration) {
        this.screenConfig = checkNotNull(configuration);
        LOGGER.info("Screen config updated with {}", screenConfig);

        setChanged();
        notifyObservers(ChangeType.SCREENCONFIG);

        calculate();
    }

    @Override
    public T getFractalConfig() {
        return fractalConfig;
    }

    @Override
    public void setFractalConfig(T configuration) {
        this.fractalConfig = checkNotNull(configuration);
        LOGGER.info("Fractal config updated with {}", fractalConfig);

        setChanged();
        notifyObservers(ChangeType.FRACTALCONFIG);

        calculate();
    }

    /**
     * Do the calculation based on configurations.
     */
    protected abstract void calculate();

}
