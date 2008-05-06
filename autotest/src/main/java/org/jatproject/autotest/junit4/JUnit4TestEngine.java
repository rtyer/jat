package org.jatproject.autotest.junit4;

import java.util.ArrayList;
import java.util.List;

import org.jatproject.autotest.TestEngine;
import org.jatproject.autotest.TestListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.RunListener;

public class JUnit4TestEngine implements TestEngine {
	private List<RunListener> listeners = new ArrayList<RunListener>();
	private JUnit4Asserter asserter;

	public JUnit4TestEngine() {
		this(new JUnit4Asserter());
	}

	public JUnit4TestEngine(JUnit4Asserter asserter) {
		this.asserter = asserter;
	}

	public void addListener(TestListener listener) {
		listeners.add(new JUnit4TestListener(listener));
	}

	public boolean run(Class<?> clazz) {
		JUnitCore junit4 = new JUnitCore();
		if (asserter.isTest(clazz)) {

			for (RunListener listener : listeners) {
				junit4.addListener(listener);
			}
			junit4.run(clazz);
			return true;

		}
		return false;
	}

}
