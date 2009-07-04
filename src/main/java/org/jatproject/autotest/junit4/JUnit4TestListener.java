package org.jatproject.autotest.junit4;

import java.util.HashMap;
import java.util.Map;

import org.jatproject.autotest.TestListener;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class JUnit4TestListener extends RunListener {
	private Map<Description, Object> locks = new HashMap<Description, Object>();

	@Override
	public void testStarted(Description description) throws Exception {
		super.testStarted(description);
		locks.put(description, new Object());
	}

	private final TestListener listener;

	public JUnit4TestListener(TestListener listener) {
		this.listener = listener;
	}

	@Override
	public void testFailure(Failure failure) throws Exception {
		super.testFailure(failure);
		listener.testFailed(failure.getTestHeader(), failure.getException());
		locks.remove(failure.getDescription());
	}

	@Override
	public void testFinished(Description description) throws Exception {
		super.testFinished(description);
		if (locks.containsKey(description)) {
			listener.testPassed(description.getDisplayName());
			locks.remove(description);
		}
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
