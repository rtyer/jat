package org.jatproject.autotest.junit4;

import org.jatproject.autotest.TestListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class JUnit4TestListenerTest {
	private Mockery mockery;

	@BeforeMethod
	public void beforeEach() {
		mockery = new Mockery();
		mockery.setImposteriser(ClassImposteriser.INSTANCE);
	}

	@Test
	public void shouldCallTestFailedWhenTestFailedIsCalled() throws Exception {
		final TestListener listener = mockery.mock(TestListener.class);
		final Failure failure = mockery.mock(Failure.class);

		final String className = "className";
		final String methodName = "methodName";

		final Throwable throwable = new Exception();
		mockery.checking(new Expectations() {
			{
				one(failure).getTestHeader();
				will(returnValue(className + "." + methodName));

				one(failure).getException();
				will(returnValue(throwable));

				one(failure).getDescription();

				one(listener).testFailed(className + "." + methodName,
						throwable);
			}
		});

		new JUnit4TestListener(listener).testFailure(failure);

		mockery.assertIsSatisfied();
	}

	@Test
	public void shouldCallTestPassWhenNonFailedTestRuns() throws Exception {
		final TestListener listener = mockery.mock(TestListener.class);
		final Description description = mockery.mock(Description.class);

		mockery.checking(new Expectations() {
			{
				one(description).getDisplayName();
				will(returnValue("method"));
				one(listener).testPassed("method");
			}
		});

		JUnit4TestListener testedListener = new JUnit4TestListener(listener);
		testedListener.testStarted(description);
		testedListener.testFinished(description);

		mockery.assertIsSatisfied();
	}

	@Test
	public void shouldCallTestsEndedWhenTestRunFinishedIsCalled()
			throws Exception {
		final TestListener listener = mockery.mock(TestListener.class);

		mockery.checking(new Expectations() {
			{
				one(listener).testsEnded();
			}
		});

		new JUnit4TestListener(listener).testRunFinished(null);

		mockery.assertIsSatisfied();
	}

	@Test
	public void shouldCallTestsStartedWhenTestRunStartedIsCalled()
			throws Exception {
		final TestListener listener = mockery.mock(TestListener.class);

		mockery.checking(new Expectations() {
			{
				one(listener).testsStarted();
			}
		});

		new JUnit4TestListener(listener).testRunStarted(null);

		mockery.assertIsSatisfied();
	}
}
