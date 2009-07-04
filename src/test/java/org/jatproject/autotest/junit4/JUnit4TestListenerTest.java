package org.jatproject.autotest.junit4;

import org.jatproject.autotest.TestListener;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.testng.annotations.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Test
public class JUnit4TestListenerTest {

	public void shouldCallTestFailedWhenTestFailedIsCalled() throws Exception
    {
		TestListener listener = mock(TestListener.class);
		Failure failure = mock(Failure.class);

		String className = "className";
		String methodName = "methodName";

		Throwable throwable = new Exception();

        when(failure.getTestHeader()).thenReturn(className + "." + methodName);
        when(failure.getException()).thenReturn(throwable);

        new JUnit4TestListener(listener).testFailure(failure);

        verify(failure).getDescription();
        verify(listener).testFailed(className + "." + methodName, throwable);
	}

	public void shouldCallTestPassWhenNonFailedTestRuns() throws Exception
    {
		TestListener listener = mock(TestListener.class);
		Description description = mock(Description.class);

        when(description.getDisplayName()).thenReturn("method");

		JUnit4TestListener testedListener = new JUnit4TestListener(listener);
		testedListener.testStarted(description);
		testedListener.testFinished(description);

        verify(listener).testPassed("method");
	}

	public void shouldCallTestsEndedWhenTestRunFinishedIsCalled() throws Exception
    {
		TestListener listener = mock(TestListener.class);

		new JUnit4TestListener(listener).testRunFinished(null);
        verify(listener).testsEnded();
	}

	public void shouldCallTestsStartedWhenTestRunStartedIsCalled() throws Exception
    {
		TestListener listener = mock(TestListener.class);
		new JUnit4TestListener(listener).testRunStarted(null);

        verify(listener).testsStarted();
	}
}
