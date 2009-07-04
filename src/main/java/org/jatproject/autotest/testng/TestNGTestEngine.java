package org.jatproject.autotest.testng;

import java.util.ArrayList;
import java.util.List;
import org.jatproject.autotest.TestEngine;
import org.jatproject.autotest.TestListener;
import org.testng.ITestListener;
import org.testng.TestNG;

public class TestNGTestEngine implements TestEngine
{
    private final TestNGTestAsserter asserter;
    private List<ITestListener> listeners = new ArrayList<ITestListener>();

    public TestNGTestEngine()
    {
        this(new TestNGTestAsserter());
    }

    public TestNGTestEngine(TestNGTestAsserter asserter)
    {
        this.asserter = asserter;
    }

    public void addListener(TestListener listener)
    {
        listeners.add(new TestNGTestListener(listener));
    }

    public boolean run(Class<?> clazz)
    {
        if(asserter.isTest(clazz))
        {
            TestNG testng = new TestNG();
            testng.setVerbose(0);
            testng.setUseDefaultListeners(false);

            for(ITestListener listener : listeners) testng.addListener(listener);

            testng.setTestClasses(new Class[] {clazz});
            testng.run();
            return true;
        }
        
        return false;
    }
}