package model;

import com.google.common.base.MoreObjects;

/**
 * Contains info of drawing area size.
 */
public class ScreenConfig {

    private final int screenWidth;
    private final int screenHeight;

    /**
     * Constructor.
     *
     * @param screenWidth  width
     * @param screenHeight height
     */
    public ScreenConfig(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public int width() {
        return screenWidth;
    }

    public int height() {
        return screenHeight;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("w", screenWidth)
                .add("h", screenHeight)
                .toString();
    }
}
