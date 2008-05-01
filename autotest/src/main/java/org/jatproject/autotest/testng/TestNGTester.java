package org.jatproject.autotest.testng;

import org.jatproject.autotest.TestListener;
import org.jatproject.autotest.Tester;
import org.testng.TestNG;

public class TestNGTester implements Tester
{
    private TestNG testng;

    public TestNGTester()
    {
        this(new TestNG());
    }

    public TestNGTester(TestNG testng)
    {
        this.testng = testng;

        testng.setVerbose(0);
        testng.setUseDefaultListeners(false);
    }

    public void addTestListener(TestListener listener)
    {
        testng.addListener(new TestNGTestListener(listener));
    }

    public void runTests(Class... testClasses)
    {
        if(testClasses.length > 0)
        {
            testng.setTestClasses(testClasses);
            testng.run();
        }
    }
}