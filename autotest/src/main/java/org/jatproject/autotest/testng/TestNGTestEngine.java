package org.jatproject.autotest.testng;

import org.jatproject.autotest.TestEngine;
import org.jatproject.autotest.TestListener;
import org.testng.TestNG;

public class TestNGTestEngine implements TestEngine
{
    private final TestNG testng;
    private final TestNGTestAsserter asserter;

    public TestNGTestEngine()
    {
        this(new TestNG(), new TestNGTestAsserter());
    }

    public TestNGTestEngine(TestNG testng, TestNGTestAsserter asserter)
    {
        this.testng = testng;
        this.asserter = asserter;

        testng.setVerbose(0);
        testng.setUseDefaultListeners(false);
    }

    public void addListener(TestListener listener)
    {
        testng.addListener(new TestNGTestListener(listener));
    }

    public boolean run(Class<?> clazz)
    {
        if(asserter.isTest(clazz))
        {
            testng.setTestClasses(new Class[] {clazz});
            testng.run();
            return true;
        }
        
        return false;
    }
}