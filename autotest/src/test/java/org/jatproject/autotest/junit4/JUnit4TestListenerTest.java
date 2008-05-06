package org.jatproject.autotest.junit4;

import org.jatproject.autotest.TestListener;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.runner.notification.Failure;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class JUnit4TestListenerTest {
	private Mockery mockery;

	@AfterMethod
	public void afterEach() {
		mockery.assertIsSatisfied();
	}

	@BeforeMethod
	public void beforeEach() {
		mockery = new Mockery();
	}

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

				one(listener).testFailed(className + "." + methodName,
						throwable);
			}
		});

		new JUnit4TestListener(listener).testFailure(failure);
	}

	public void shouldCallTestsEndedWhenTestRunFinishedIsCalled()
			throws Exception {
		final TestListener listener = mockery.mock(TestListener.class);

		mockery.checking(new Expectations() {
			{
				one(listener).testsEnded();
			}
		});

		new JUnit4TestListener(listener).testRunFinished(null);
	}

	public void shouldCallTestsStartedWhenTestRunStartedIsCalled()
			throws Exception {
		final TestListener listener = mockery.mock(TestListener.class);

		mockery.checking(new Expectations() {
			{
				one(listener).testsStarted();
			}
		});

		new JUnit4TestListener(listener).testRunStarted(null);
	}
}
