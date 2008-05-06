package org.jatproject.autotest.junit4;

import org.jatproject.autotest.TestEngine;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class JUnit4TestAsserterTest {
	private JUnit4Asserter asserter;

	@BeforeMethod
	protected void beforeEach() {
		asserter = new JUnit4Asserter();
	}

	@Test
	public void annotatedJUnit4TestShouldReturnTrue() {
		assertTrue(asserter.isTest(SampleJunit4AnnotationTest.class));
	}

	@Test
	public void superclassJUnit4TestShouldReturnTrue() {
		assertTrue(asserter.isTest(SampleJUnit4SuperclassTest.class));
	}

	@Test
	public void nonJUnitTestsShouldReturnFalse() {
		assertFalse(asserter.isTest(TestEngine.class));
	}

	@Test
	public void testNGTestsShouldReturnFalse() {
		assertFalse(asserter.isTest(this.getClass()));
	}
}
