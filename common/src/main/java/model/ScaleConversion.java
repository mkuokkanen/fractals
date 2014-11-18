package model;

/**
 * Converts values between fractal plane and screen pixels.
 *
 * @author Mikko Kuokkanen
 */
class ScaleConversion {

    /**
     * Hide utility class constructor.
     */
    private ScaleConversion() {
    }

    /**
     * @param xMin        mathematical plane min x
     * @param xMax        mathematical plane max x
     * @param screenWidth screen width in pixels
     * @param x           mathematical plane point x
     * @return incoming mathematical point x converted to screen x pixel
     */
    public static int xFromFractalToScreen(final double xMin, final double xMax, final int screenWidth, final double x) {
        return (int) Math.round((x - xMin) / (xMax - xMin) * screenWidth);
    }

    /**
     * @param yMin         mathematical plane min y
     * @param yMax         mathematical plane max y
     * @param screenHeight screen height in pixels
     * @param y            mathematical plane point y
     * @return incoming mathematical point y converted to screen y pixel
     */
    public static int yFromFractalToScreen(final double yMin, final double yMax, final int screenHeight, final double y) {
        return (int) Math.round(screenHeight - ((y - yMin) / (yMax - yMin) * screenHeight));
    }

    /**
     * @param xMin        mathematical plane min x
     * @param xMax        mathematical plane max x
     * @param screenWidth screen width in pixels
     * @param x           point x in screen pixels
     * @return incoming screen x converted to mathematical plane point x
     */
    public static double xFromScreenToFractal(final double xMin, final double xMax, final int screenWidth, final int x) {
        return xMin + ((double) x / screenWidth * (xMax - xMin));
    }

    /**
     * @param yMin         mathematical plane min y
     * @param yMax         mathematical plane max y
     * @param screenHeight screen height in pixels
     * @param y            point y in screen pixels
     * @return incoming screen y converted to mathematical plane point y
     */
    public static double yFromScreenToFractal(final double yMin, final double yMax, final int screenHeight, final int y) {
        return yMin + ((screenHeight - (double) y) / screenHeight * (yMax - yMin));
    }

}
