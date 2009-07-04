package org.jatproject.autotest.listeners;

import org.jatproject.autotest.TestListener;

public class ConsoleTestListener implements TestListener
{
    public void testsStarted()
    {
        System.out.println("Tests Starting********************");
    }

    public void testPassed(String testName)
    {
        System.out.println(testName + " Passed");
    }

    public void testFailed(String testName, Throwable failure)
    {
        System.out.println(testName + " FAILED");
        failure.printStackTrace(System.out);
    }

    public void testsEnded()
    {
        System.out.println("Tests Finished*********************");
    }
}
