package org.jatproject.autotest.testng;

import org.jatproject.autotest.TestEngine;
import org.jatproject.autotest.TestListener;
import org.testng.TestNG;

import java.util.HashSet;
import java.util.Set;

public class TestNGTestEngine implements TestEngine
{
    private final TestNG testng;
    private final TestNGTestAsserter asserter;
    private final Set<Class> tests;

    public TestNGTestEngine()
    {
        this(new TestNG(), new TestNGTestAsserter());
    }

    public TestNGTestEngine(TestNG testng, TestNGTestAsserter asserter)
    {
        this.testng = testng;
        this.asserter = asserter;
        this.tests = new HashSet<Class>();

        testng.setVerbose(0);
        testng.setUseDefaultListeners(false);
    }

    public void addListener(TestListener listener)
    {
        testng.addListener(new TestNGTestListener(listener));
    }

    public boolean add(Class<?> clazz)
    {
        if(asserter.isTest(clazz))
        {
            tests.add(clazz);
            return true;
        }

        return false;
    }

    public void run()
    {
        if(tests.isEmpty() == false)
        {
            testng.setTestClasses(tests.toArray(new Class[tests.size()]));
            testng.run();
        }
    }
}