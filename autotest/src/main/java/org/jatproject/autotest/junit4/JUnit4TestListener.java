package org.jatproject.autotest.junit4;

import org.jatproject.autotest.TestListener;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class JUnit4TestListener extends RunListener {
	private final TestListener listener;
	
	public JUnit4TestListener(TestListener listener) {
		this.listener = listener;
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		super.testFailure(failure);
		listener.testFailed(failure.getTestHeader(), failure.getException());
	}

	@Override
	//this method is called whether the test passes or fails.  There is no testPassed equivalent.
	public void testFinished(Description description) throws Exception {
		super.testFinished(description);
	}

	@Override
	public void testRunFinished(Result result) throws Exception {
		super.testRunFinished(result);
		listener.testsEnded();
	}

	@Override
	public void testRunStarted(Description description) throws Exception {
		super.testRunStarted(description);
		listener.testsStarted();
	}

}
