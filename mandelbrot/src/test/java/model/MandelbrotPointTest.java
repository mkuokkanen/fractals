package model;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mkuokkanen on 13.6.2017.
 */
public class MandelbrotPointTest {

	private MandelbrotPoint mpCenter;
	private MandelbrotPoint mpOut;

	@Before
	public void setUp() throws Exception {
		mpCenter = new MandelbrotPoint(0.0, -0.0, 200);
		mpOut = new MandelbrotPoint(9.876, -6.543, 200);
	}

	@Test
	public void getIterations() throws Exception {
		assertEquals(200, mpOut.getIterations());
	}

	@Test
	public void getcReal() throws Exception {
		assertEquals(9.876, mpOut.getcReal(), 0.001);
	}

	@Test
	public void getcImaginary() throws Exception {
		assertEquals(-6.543, mpOut.getcImaginary(), 0.001);
	}

	@Test
	public void getEscapeIterationForCenter() throws Exception {
		assertEquals(mpCenter.getIterations(), mpCenter.getEscapeIteration());
	}

	@Test
	public void getEscapeIterationForOutside() throws Exception {
		assertEquals(1, mpOut.getEscapeIteration());
	}

	@Test
	public void isBoundedCenter() throws Exception {
		assertTrue(mpCenter.isBounded());
	}

	@Test
	public void isBoundedOutside() throws Exception {
		assertFalse(mpOut.isBounded());
	}

}
