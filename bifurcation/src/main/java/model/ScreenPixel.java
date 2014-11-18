package model;

import com.google.common.base.MoreObjects;

/**
 * Used to
 */
public class ScreenPixel {

    public final int x;
    public final int y;

    public ScreenPixel(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("pix")
                .add("x", x)
                .add("y", y)
                .toString();
    }
}
