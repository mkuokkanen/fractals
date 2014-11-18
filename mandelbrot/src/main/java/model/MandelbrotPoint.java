package model;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * One point describing
 * <p/>
 * theory:
 * z is complex number to iterate
 * c is complex number constant describing point in complex plane to investigate
 * <p/>
 * c = (x,y)
 * z(0) = (0,0)
 * z(n+1) = z(n)^2 + c
 * <p/>
 * if |z| >= 2, we can assume iteration will escape to infinity
 * <p/>
 * Created 17.6.2005.
 *
 * @author Mikko Kuokkanen
 */
public class MandelbrotPoint {

    private final int iterations;
    private final double cReal;
    private final double cImaginary;
    private final int escapeIteratoin;
    private final boolean bounded;


    public MandelbrotPoint(final double x, final double y, final int iterations) {
        checkArgument(iterations >= 1, "iterations must be bigger than 1, was %s", iterations);

        this.cReal = x;
        this.cImaginary = y;
        this.iterations = iterations;

        this.escapeIteratoin = calculateEscapeValue();
        this.bounded = escapeIteratoin == iterations;
    }

    private int calculateEscapeValue() {
        double z1Real = 0;
        double z1Imaginary = 0;

        double z0Real = 0;
        double z0Imaginary = 0;

        int i = 0;

        while (i < iterations) {
            i++;

            /*
             from maol:
             z*z = (a1*a2 - b1*b2) + (a1*b2 + a2*b1)i = (aa - bb) + (ab + ab)i

             => Re(z*z) = (aa - bb)
             => Im(z*z) = (ab + ab)
			*/
            z1Real = (z0Real * z0Real - z0Imaginary * z0Imaginary);
            z1Imaginary = (z0Real * z0Imaginary + z0Real * z0Imaginary);

			/*
             from maol:
             z+c == (a1+a2) + (b1+b2)i
                => Re(z+c) = (a1+a2)
                => Im(z+c) = (b1+b2)
			*/
            z1Real += cReal;
            z1Imaginary += cImaginary;

			/*
             from maol:
             |z| = square root(z0Real^2 + b^2)
             
             => sqrt(z0Real*z0Real + b*b)
             
             if sqrt(z0Real*z0Real + b*b) >= 2  --> iteration goes to infinity
                => z0Real*z0Real+b*b >= 4
			*/
            if (z1Real * z1Real + z1Imaginary * z1Imaginary >= 4) {
                break;
            }

            z0Real = z1Real;
            z0Imaginary = z1Imaginary;
        }

        return i;
    }

    public int getIterations() {
        return iterations;
    }

    public double getcReal() {
        return cReal;
    }

    public double getcImaginary() {
        return cImaginary;
    }

    public int getEscapeIteration() {
        return escapeIteratoin;
    }

    public boolean isBounded() {
        return bounded;
    }
}
