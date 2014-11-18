package model;

import com.google.common.base.MoreObjects;

/**
 * Created by mkuokkanen on 26.11.2014.
 */
public class ScreenPixel {

    public final int x;
    public final int y;
    public final MandelbrotPoint mp;

    public ScreenPixel(final int x, final int y, MandelbrotPoint mp) {
        this.x = x;
        this.y = y;
        this.mp = mp;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("pix")
                .add("x", x)
                .add("y", y)
                .toString();
    }
}
